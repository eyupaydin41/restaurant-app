package com.restaurant.restaurantservice.repository;

import com.restaurant.restaurantservice.model.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {
    Optional<CustomerReview> findByUserIdAndRestaurantId(Long userId, Long restaurantId);
}