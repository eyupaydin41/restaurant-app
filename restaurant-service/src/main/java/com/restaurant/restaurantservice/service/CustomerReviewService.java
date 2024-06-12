package com.restaurant.restaurantservice.service;

import com.restaurant.restaurantservice.dto.CustomerReviewRequest;
import com.restaurant.restaurantservice.dto.CustomerReviewResponse;
import com.restaurant.restaurantservice.model.CustomerReview;

import java.util.List;

public interface CustomerReviewService {

    void createReview(CustomerReviewRequest customerReviewRequest);

    List<CustomerReviewResponse> getAllReviews();

    CustomerReviewResponse getReviewByID(Long id);

    void updateReview(Long id, CustomerReviewRequest customerReviewRequest);

    void deleteReview(Long id);

    CustomerReviewResponse entityToDto(CustomerReview customerReview);
}
