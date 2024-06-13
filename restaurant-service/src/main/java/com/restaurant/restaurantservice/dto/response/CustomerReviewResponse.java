package com.restaurant.restaurantservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerReviewResponse {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String comment;
    private Integer serviceRating;
    private Integer tasteRating;
    private Integer priceRating;
    private Double averageRating;
}