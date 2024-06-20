package com.restaurant.restaurantservice.dto;

import lombok.Data;

@Data
public class DeleteReviewRequest {
    private Long userId;
    private Long reviewId;
}
