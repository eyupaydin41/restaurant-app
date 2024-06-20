package com.restaurant.restaurantservice.dto;

import lombok.Data;

@Data
public class DeleteRestaurantRequest {

    private Long userId;
    private Long restaurantId;
}
