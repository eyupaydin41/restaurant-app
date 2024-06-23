package com.restaurant.restaurantservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteRestaurantRequest {

    private Long userId;
    private Long restaurantId;
}
