package com.restaurant.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewRequest {
    private Long userId;
    private Long restaurantId;
    private String comment;
    private Integer serviceRating;
    private Integer tasteRating;
    private Integer priceRating;
}
