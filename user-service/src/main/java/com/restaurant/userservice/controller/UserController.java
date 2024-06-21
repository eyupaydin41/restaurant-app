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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Service", description = "Kullanıcı işlemlerini yöneten servis")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Tüm kullanıcıları getir", description = "Bu endpoint tüm kullanıcıları döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başarılı",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/user\"}")))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Kullanıcıyı ID ile bul", description = "Bu endpoint belirli bir ID'ye sahip kullanıcıyı döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başarılı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"User not found with id: ?\", \"details\": \"uri=/user/{id}\"}")))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getUserById(
            @Parameter(description = "Kullanıcı ID'si", required = true)
            @PathVariable Long id) {
        return userService.getUserByID(id);
    }

    @Operation(summary = "Yeni bir kullanıcı oluştur", description = "Bu endpoint yeni bir kullanıcı oluşturur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kullanıcı oluşturuldu",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "400", description = "Geçersiz giriş",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"400\", \"message\": \"Invalid input\", \"details\": \"uri=/user\"}"))),
            @ApiResponse(responseCode = "409", description = "Kullanıcı mevcut",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"409\", \"message\": \"There is already a user with the email address: ?\", \"details\": \"uri=/user\"}"))),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/user\"}")))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> createUser(
            @Parameter(description = "Kullanıcı bilgileri", required = true)
            @RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @Operation(summary = "Kullanıcıyı güncelle", description = "Bu endpoint mevcut bir kullanıcıyı günceller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Güncelleme başarılı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "400", description = "Geçersiz giriş",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"400\", \"message\": \"Invalid input\", \"details\": \"uri=/user\"}"))),
            @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"User not found with id: ?\", \"details\": \"uri=/user\"}"))),
            @ApiResponse(responseCode = "409", description = "Kullanıcı mevcut",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"409\", \"message\": \"There is already a user with the email address: ?\", \"details\": \"uri=/user\"}"))),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/user\"}")))
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> updateUser(
            @Parameter(description = "Güncellenmiş kullanıcı bilgileri", required = true)
            @RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @Operation(summary = "Kullanıcıyı sil", description = "Bu endpoint mevcut bir kullanıcıyı siler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Silme başarılı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"User not found with id: ?\", \"details\": \"uri=/user/{id}\"}"))),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/user/{id}\"}")))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> deleteUser(
            @Parameter(description = "Kullanıcı ID'si", required = true)
            @PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
