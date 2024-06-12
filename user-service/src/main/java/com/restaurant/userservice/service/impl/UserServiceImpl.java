package com.restaurant.userservice.service.impl;

import com.restaurant.restaurantservice.exception.ResourceNotFoundException;
import com.restaurant.userservice.dto.UserRequest;
import com.restaurant.userservice.dto.UserResponse;
import com.restaurant.userservice.model.User;
import com.restaurant.userservice.repository.UserRepository;
import com.restaurant.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

    }


    @Override
    public UserResponse getUserByID(Long id) {
        return userRepository.findById(id).map(this::entityToDto).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public void updateUser(Long id, UserRequest userRequest) {
        userRepository.findById(id).ifPresentOrElse(
                user -> {
                    user.setName(userRequest.getName());
                    user.setSurname(userRequest.getSurname());
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(userRequest.getPassword());
                    userRepository.save(user);
                },
                () -> {
                    throw new ResourceNotFoundException("User not found with id: " + id);
                }
        );
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("User not found with id: " + id);
                }
        );
    }

    @Override
    public UserResponse entityToDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }


}
