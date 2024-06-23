package com.restaurant.restaurantservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteReviewRequest {
    private Long userId;
    private Long reviewId;
}
