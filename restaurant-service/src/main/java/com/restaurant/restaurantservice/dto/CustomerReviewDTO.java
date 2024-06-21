package com.restaurant.restaurantservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewDTO {

    @Schema(description = "Değerlendirme ID", example = "1")
    private Long id;

    @Schema(description = "Kullanıcı ID'si", required = true, example = "123")
    private Long userId;

    @Schema(description = "Restoran ID'si", required = true, example = "456")
    private Long restaurantId;

    @Schema(description = "Yorum", example = "Harika bir deneyim yaşadım.")
    private String comment;

    @Schema(description = "Hizmet kalitesi değerlendirmesi (1-10 arası)", minimum = "1", maximum = "10", example = "9")
    @Min(value = 1)
    @Max(value = 10)
    private Integer serviceRating;

    @Schema(description = "Lezzet değerlendirmesi (1-10 arası)", minimum = "1", maximum = "10", example = "8")
    @Min(value = 1)
    @Max(value = 10)
    private Integer tasteRating;

    @Schema(description = "Fiyat performans değerlendirmesi (1-10 arası)", minimum = "1", maximum = "10", example = "7")
    @Min(value = 1)
    @Max(value = 10)
    private Integer priceRating;
}
