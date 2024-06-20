package com.restaurant.userservice.service.impl;

import com.restaurant.userservice.dto.UserDTO;
import com.restaurant.userservice.dto.response.UserResponse;
import com.restaurant.userservice.exception.DuplicateUserException;
import com.restaurant.userservice.exception.UserNotFoundException;
import com.restaurant.userservice.model.User;
import com.restaurant.userservice.model.response.Response;
import com.restaurant.userservice.model.response.ResponseBuilderA;
import com.restaurant.userservice.repository.UserRepository;
import com.restaurant.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ResponseBuilderA responseBuilderA;

    @Override
    public ResponseEntity<Response> createUser(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateUserException(userDTO.getEmail());
        }

        User user = User.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .userType(userDTO.getUserType())
                .restaurantList(null)
                .build();

        userRepository.save(user);

        return responseBuilderA.buildResponse(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Response> getAllUsers() {
        List<UserDTO> list = userRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();

        String message = "The user list has been successfully fetched.";
        return responseBuilderA.buildResponse(message, HttpStatus.OK, list);

    }


    @Override
    public ResponseEntity<Response> getUserByID(Long id) {
        UserResponse userResponse = userRepository.findById(id).map(this::EntityToResponse)
                .orElseThrow(() -> new UserNotFoundException(id));


        String message = "User found with id: " + id;

        return responseBuilderA.buildResponse(message, HttpStatus.OK, userResponse);

    }

    @Override
    public ResponseEntity<Response> updateUser(UserDTO userDTO) {
        userRepository.findById(userDTO.getId()).ifPresentOrElse(
                user -> {
                    user.setName(userDTO.getName());
                    user.setSurname(userDTO.getSurname());
                    user.setEmail(userDTO.getEmail());
                    user.setPassword(userDTO.getPassword());
                    user.setUserType(userDTO.getUserType());
                    userRepository.save(user);
                },
                () -> {
                    throw new UserNotFoundException(userDTO.getId());
                }
        );

        return responseBuilderA.buildResponse(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Response> deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(
                userRepository::delete,

                () -> {
                    throw new UserNotFoundException(id);
                }
        );

        return responseBuilderA.buildResponse(HttpStatus.OK);

    }

    @Override
    public UserDTO entityToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }


    public UserResponse EntityToResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }



}
