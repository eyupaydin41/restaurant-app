package com.restaurant.userservice.service.impl;

import com.restaurant.userservice.dto.UserDTO;
import com.restaurant.userservice.exception.UserNotFoundException;
import com.restaurant.userservice.model.User;
import com.restaurant.userservice.model.response.Response;
import com.restaurant.userservice.model.response.ResponseBuilder;
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
    private final ResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<Response> createUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();

        userRepository.save(user);

        String message = "The user has been created successfully.";
        return responseBuilder.buildResponse(message, HttpStatus.CREATED,userDTO);

    }

    @Override
    public ResponseEntity<Response> getAllUsers() {
        List<UserDTO> list = userRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();

        String message = "The user list has been successfully fetched.";
        return responseBuilder.buildResponse(message,HttpStatus.OK,list);

    }


    @Override
    public ResponseEntity<Response> getUserByID(Long id) {
        UserDTO userDTO = userRepository.findById(id).map(this::entityToDto)
                .orElseThrow(() -> new UserNotFoundException(id));

        String message = "User found with id: " + id;

        return responseBuilder.buildResponse(message,HttpStatus.OK,userDTO);

    }

    @Override
    public ResponseEntity<Response> updateUser(Long id, UserDTO userDTO) {
        userRepository.findById(id).ifPresentOrElse(
                user -> {
                    user.setName(userDTO.getName());
                    user.setSurname(userDTO.getSurname());
                    user.setEmail(userDTO.getEmail());
                    user.setPassword(userDTO.getPassword());
                    userRepository.save(user);
                },
                () -> {
                    throw new UserNotFoundException(id);
                }
        );

        String message = "Successfully updated user with ID: " + id;
        return responseBuilder.buildResponse(message,HttpStatus.OK,userDTO);

    }

    @Override
    public ResponseEntity<Response> deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        UserDTO userDTO = entityToDto(user);
        userRepository.delete(user);

        String message = "Successfully deleted user with ID: " + id;
        return responseBuilder.buildResponse(message,HttpStatus.OK,userDTO);

    }

    @Override
    public UserDTO entityToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }


}
