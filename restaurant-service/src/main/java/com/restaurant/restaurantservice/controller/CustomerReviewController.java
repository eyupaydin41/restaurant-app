package com.restaurant.restaurantservice.controller;

import com.restaurant.restaurantservice.dto.CustomerReviewDTO;
import com.restaurant.restaurantservice.dto.DeleteReviewRequest;
import com.restaurant.restaurantservice.exception.InvalidRatingException;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.service.CustomerReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Customer Review Service", description = "Müşteri değerlendirme işlemlerini yöneten servis")
public class CustomerReviewController {

    private final CustomerReviewService customerReviewService;

    @Operation(summary = "Tüm müşteri değerlendirmelerini getir", description = "Bu endpoint tüm müşteri değerlendirmelerini döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başarılı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/reviews\"}")))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getAllReviews() {
        return customerReviewService.getAllReviews();
    }

    @Operation(summary = "Müşteri değerlendirmesini ID ile bul", description = "Bu endpoint belirli bir ID'ye sahip müşteri değerlendirmesini döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başarılı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "404", description = "Değerlendirme bulunamadı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"Review not found with id: ?\", \"details\": \"uri=/reviews/{id}\"}"))),
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getReviewById(
            @Parameter(description = "Değerlendirme ID'si", required = true)
            @PathVariable Long id) {
        return customerReviewService.getReviewByID(id);
    }

    @Operation(summary = "Yeni bir müşteri değerlendirmesi oluştur", description = "Bu endpoint yeni bir müşteri değerlendirmesi oluşturur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Değerlendirme oluşturuldu",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "400", description = "Geçersiz giriş",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"400\", \"message\": \"Invalid rating value\", \"details\": \"uri=/reviews\"}"))),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/reviews\"}")))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> createReview(
            @Parameter(description = "Değerlendirme bilgileri", required = true)
            @RequestBody @Valid CustomerReviewDTO customerReviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRatingException();
        }
        return customerReviewService.createReview(customerReviewDTO);
    }

    @Operation(summary = "Mevcut bir müşteri değerlendirmesini güncelle", description = "Bu endpoint mevcut bir müşteri değerlendirmesini günceller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Güncelleme başarılı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "400", description = "Geçersiz giriş",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"400\", \"message\": \"Invalid rating value\", \"details\": \"uri=/reviews\"}"))),
            @ApiResponse(responseCode = "404", description = "Değerlendirme bulunamadı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"Review not found with id: ?\", \"details\": \"uri=/reviews\"}"))),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/reviews\"}")))
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> updateReview(
            @Parameter(description = "Güncellenmiş değerlendirme bilgileri", required = true)
            @RequestBody @Valid CustomerReviewDTO customerReviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRatingException();
        }
        return customerReviewService.updateReview(customerReviewDTO);
    }

    @Operation(summary = "Mevcut bir müşteri değerlendirmesini sil", description = "Bu endpoint mevcut bir müşteri değerlendirmesini siler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Silme başarılı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{}"))),
            @ApiResponse(responseCode = "404", description = "Değerlendirme bulunamadı",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"Review not found with id: ?\", \"details\": \"uri=/reviews\"}"))),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/reviews\"}")))
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> deleteReview(
            @Parameter(description = "Silme talebi", required = true)
            @RequestBody DeleteReviewRequest deleteReviewRequest) {
        return customerReviewService.deleteReview(deleteReviewRequest);
    }
}
