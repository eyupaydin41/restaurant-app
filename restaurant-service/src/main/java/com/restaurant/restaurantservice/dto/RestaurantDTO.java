package com.restaurant.restaurantservice.dto;

import com.restaurant.restaurantservice.model.RestaurantCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private String name;
    private RestaurantCategory category;
    private String address;
    private String phone;
    private String email;
    private String description;

}
