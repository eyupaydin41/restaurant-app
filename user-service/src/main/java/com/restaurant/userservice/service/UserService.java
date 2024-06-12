package com.restaurant.userservice.service;

import com.restaurant.userservice.dto.UserRequest;
import com.restaurant.userservice.dto.UserResponse;
import com.restaurant.userservice.model.User;

import java.util.List;

public interface UserService {
    void createUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse entityToDto(User user);

    UserResponse getUserByID(Long id);

    void updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
