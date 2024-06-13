package com.restaurant.restaurantservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewDTO {
    private Long userId;
    private Long restaurantId;
    private String comment;

    @Min(value = 1)
    @Max(value = 10)
    private Integer serviceRating;
    @Min(value = 1)
    @Max(value = 10)
    private Integer tasteRating;
    @Min(value = 1)
    @Max(value = 10)
    private Integer priceRating;
}
