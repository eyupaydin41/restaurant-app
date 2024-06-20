package com.restaurant.userservice.controller;

import com.restaurant.userservice.dto.UserDTO;
import com.restaurant.userservice.model.response.Response;
import com.restaurant.userservice.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        return userService.getUserByID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> createUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


}
