package com.restaurant.userservice.dto.response;

import com.restaurant.userservice.model.RestaurantCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private Long userId;
    private String name;
    private RestaurantCategory category;
    private String address;
    private String phone;
    private String email;
    private String description;
    private Double rating;
    private Double serviceRatingAverage;
    private Double tasteRatingAverage;
    private Double priceRatingAverage;
    private Double ratingAverage;
}
