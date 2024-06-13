package com.restaurant.restaurantservice.dto.response;

import com.restaurant.restaurantservice.dto.CustomerReviewDTO;
import com.restaurant.restaurantservice.model.RestaurantCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantWithReviewsResponse {
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
    private List<CustomerReviewDTO> customerReviews;
}
