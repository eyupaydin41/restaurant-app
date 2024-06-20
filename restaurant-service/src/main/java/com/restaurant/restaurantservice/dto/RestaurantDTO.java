package com.restaurant.restaurantservice.dto;

import com.restaurant.restaurantservice.model.RestaurantCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotNull(message = "UserID cannot be null")
    private Long userId;

    @NotNull(message = "Category cannot be null")
    private RestaurantCategory category;

    @NotBlank(message = "Address cannot be null")
    private String address;

    @NotBlank(message = "Phone cannot be null")
    private String phone;

    @NotBlank(message = "Email cannot be null")
    private String email;

    private String description;

}
