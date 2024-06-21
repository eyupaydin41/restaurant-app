package com.restaurant.restaurantservice.dto;

import com.restaurant.restaurantservice.model.RestaurantCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    @Schema(description = "Restoran ID", example = "1")
    private Long id;

    @Schema(description = "Restoran adı", required = true, example = "My Restaurant")
    @NotBlank(message = "Name cannot be null")
    private String name;

    @Schema(description = "Kullanıcı ID'si", required = true, example = "123")
    @NotNull(message = "UserID cannot be null")
    private Long userId;

    @Schema(description = "Restoran kategorisi", required = true, example = "PIZZACI")
    @NotNull(message = "Category cannot be null")
    private RestaurantCategory category;

    @Schema(description = "Restoran adresi", required = true, example = "Beşiktaş/İstanbul")
    @NotBlank(message = "Address cannot be null")
    private String address;

    @Schema(description = "Restoran telefon numarası", required = true, example = "05398765432")
    @NotBlank(message = "Phone cannot be null")
    private String phone;

    @Schema(description = "Restoran email adresi", required = true, example = "info@myrestaurant.com")
    @NotBlank(message = "Email cannot be null")
    private String email;

    @Schema(description = "Restoran açıklaması", example = "A cozy place.")
    private String description;

}
