package com.restaurant.userservice.dto;

import com.restaurant.userservice.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kullanıcıyı temsil eden DTO")
public class UserDTO {

    @Schema(description = "Kullanıcı ID'si")
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Schema(description = "Kullanıcının adı", example = "Eyüp", required = true)
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    @Schema(description = "Kullanıcının soyadı", example = "Aydın", required = true)
    private String surname;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be empty")
    @Schema(description = "Kullanıcının email adresi", example = "eyup.aydin@gmail.com", required = true)
    private String email;

    @NotBlank(message = "Password cannot be null")
    @Schema(description = "Kullanıcının şifresi", example = "password", required = true)
    private String password;

    @NotBlank(message = "UserType cannot be null")
    @Schema(description = "Kullanıcı tipi", example = "MANAGER", required = true, implementation = UserType.class)
    private UserType userType;
}
