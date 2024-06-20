package com.restaurant.userservice.service;

import com.restaurant.userservice.dto.UserDTO;
import com.restaurant.userservice.model.User;
import com.restaurant.userservice.model.response.Response;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<Response> createUser(UserDTO userDTO);

    ResponseEntity<Response> getAllUsers();

    ResponseEntity<Response> getUserByID(Long id);

    ResponseEntity<Response> updateUser(UserDTO userDTO);

    ResponseEntity<Response> deleteUser(Long id);

    UserDTO entityToDto(User user);
}
