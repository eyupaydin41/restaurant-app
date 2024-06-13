package com.restaurant.restaurantservice.service;

import com.restaurant.restaurantservice.dto.CustomerReviewDTO;
import com.restaurant.restaurantservice.model.CustomerReview;
import com.restaurant.restaurantservice.model.response.Response;
import org.springframework.http.ResponseEntity;

public interface CustomerReviewService {

    ResponseEntity<Response> createReview(CustomerReviewDTO customerReviewDTO);

    ResponseEntity<Response> getAllReviews();

    ResponseEntity<Response> getReviewByID(Long id);

    ResponseEntity<Response> updateReview(Long id, CustomerReviewDTO customerReviewDTO);

    ResponseEntity<Response> deleteReview(Long id);

    CustomerReviewDTO entityToDto(CustomerReview customerReview);
}
