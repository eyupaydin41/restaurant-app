package com.restaurant.userservice.controller;

import com.restaurant.userservice.dto.UserDTO;
import com.restaurant.userservice.model.response.Response;
import com.restaurant.userservice.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Service", description = "Kullanıcı işlemlerini yöneten servis")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Tüm kullanıcıları getir", description = "Bu endpoint tüm kullanıcıları döner")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Kullanıcıyı ID ile bul", description = "Bu endpoint belirli bir ID'ye sahip kullanıcıyı döner")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getUserById(
            @Parameter(description = "Kullanıcı ID'si", required = true)
            @PathVariable Long id) {
        return userService.getUserByID(id);
    }

    @Operation(summary = "Yeni bir kullanıcı oluştur", description = "Bu endpoint yeni bir kullanıcı oluşturur")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> createUser(
            @Parameter(description = "Kullanıcı bilgileri", required = true)
            @RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @Operation(summary = "Kullanıcıyı güncelle", description = "Bu endpoint mevcut bir kullanıcıyı günceller")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> updateUser(
            @Parameter(description = "Güncellenmiş kullanıcı bilgileri", required = true)
            @RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @Operation(summary = "Kullanıcıyı sil", description = "Bu endpoint mevcut bir kullanıcıyı siler")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> deleteUser(
            @Parameter(description = "Kullanıcı ID'si", required = true)
            @PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
