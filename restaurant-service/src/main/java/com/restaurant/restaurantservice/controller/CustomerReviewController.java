package com.restaurant.restaurantservice.controller;

import com.restaurant.restaurantservice.dto.CustomerReviewRequest;
import com.restaurant.restaurantservice.dto.CustomerReviewResponse;
import com.restaurant.restaurantservice.service.CustomerReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final CustomerReviewService customerReviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestBody CustomerReviewRequest customerReviewRequest) {
        customerReviewService.createReview(customerReviewRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateReview(@PathVariable Long id, @RequestBody CustomerReviewRequest customerReviewRequest) {
        customerReviewService.updateReview(id, customerReviewRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerReviewResponse> getAllReviews() {
        return customerReviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerReviewResponse getReviewById(@PathVariable Long id) {
        return customerReviewService.getReviewByID(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReview(@PathVariable Long id) {
        customerReviewService.deleteReview(id);
    }
}
