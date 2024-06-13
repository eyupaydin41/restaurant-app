package com.restaurant.restaurantservice.controller;

import com.restaurant.restaurantservice.dto.CustomerReviewDTO;
import com.restaurant.restaurantservice.exception.InvalidRatingException;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.service.CustomerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final CustomerReviewService customerReviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> createReview(@RequestBody @Valid CustomerReviewDTO customerReviewDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRatingException();
        }
        return customerReviewService.createReview(customerReviewDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> updateReview(@PathVariable Long id, @RequestBody @Valid CustomerReviewDTO customerReviewDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRatingException();
        }
        return customerReviewService.updateReview(id, customerReviewDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getAllReviews() {
        return customerReviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getReviewById(@PathVariable Long id) {
        return customerReviewService.getReviewByID(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> deleteReview(@PathVariable Long id) {
        return customerReviewService.deleteReview(id);
    }
}
