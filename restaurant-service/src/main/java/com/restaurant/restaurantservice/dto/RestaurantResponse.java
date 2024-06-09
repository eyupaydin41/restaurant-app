package com.restaurant.restaurantservice.dto;

import com.restaurant.restaurantservice.model.RestaurantCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private RestaurantCategory category;
    private String address;
    private String phone;
    private String email;
    private Double rating;
    private String description;
}
