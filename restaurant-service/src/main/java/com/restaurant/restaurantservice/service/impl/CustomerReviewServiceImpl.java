package com.restaurant.restaurantservice.service.impl;

import com.restaurant.restaurantservice.dto.CustomerReviewRequest;
import com.restaurant.restaurantservice.dto.CustomerReviewResponse;
import com.restaurant.restaurantservice.exception.DuplicateReviewException;
import com.restaurant.restaurantservice.exception.ResourceNotFoundException;
import com.restaurant.restaurantservice.model.CustomerReview;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.repository.RestaurantRepository;
import com.restaurant.restaurantservice.repository.CustomerReviewRepository;
import com.restaurant.restaurantservice.service.CustomerReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final CustomerReviewRepository customerReviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createReview(CustomerReviewRequest customerReviewRequest) {
        Restaurant restaurant = restaurantRepository.findById(customerReviewRequest.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + customerReviewRequest.getRestaurantId()));

        // customerReviewRequest.getUserId() -> user mevcut mu kontrolü yapılacak

        customerReviewRepository.findByUserIdAndRestaurantId(customerReviewRequest.getUserId(), customerReviewRequest.getRestaurantId())
                .orElseThrow(() -> new DuplicateReviewException("User has already reviewed this restaurant"));


        CustomerReview customerReview = CustomerReview.builder()
                .serviceRating(customerReviewRequest.getServiceRating())
                .tasteRating(customerReviewRequest.getTasteRating())
                .priceRating(customerReviewRequest.getPriceRating())
                .comment(customerReviewRequest.getComment())
                .restaurant(restaurant)
                .userId(customerReviewRequest.getUserId())
                .build();

        customerReviewRepository.save(customerReview);
        updateRestaurantRatings(restaurant);
    }


    @Override
    public List<CustomerReviewResponse> getAllReviews() {
        return customerReviewRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerReviewResponse getReviewByID(Long id) {
        return customerReviewRepository.findById(id)
                .map(this::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerReview not found with id: " + id));
    }

    @Override
    public void updateReview(Long reviewId, CustomerReviewRequest customerReviewRequest) {
        CustomerReview customerReview = customerReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerReview not found with id: " + reviewId));

        customerReview.setServiceRating(customerReviewRequest.getServiceRating());
        customerReview.setTasteRating(customerReviewRequest.getTasteRating());
        customerReview.setPriceRating(customerReviewRequest.getPriceRating());
        customerReview.setComment(customerReviewRequest.getComment());

        customerReviewRepository.save(customerReview);
        updateRestaurantRatings(customerReview.getRestaurant());
    }

    @Override
    public void deleteReview(Long reviewId) {
        CustomerReview customerReview = customerReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerReview not found with id: " + reviewId));

        customerReviewRepository.delete(customerReview);
        updateRestaurantRatings(customerReview.getRestaurant());
    }


    public CustomerReviewResponse entityToDto(CustomerReview customerReview) {
        return modelMapper.map(customerReview, CustomerReviewResponse.class);
    }

    private void updateRestaurantRatings(Restaurant restaurant) {
        List<CustomerReview> customerReviews = restaurant.getCustomerReviews();

        OptionalDouble serviceRatingAverage = customerReviews.stream().mapToInt(CustomerReview::getServiceRating).average();
        OptionalDouble tasteRatingAverage = customerReviews.stream().mapToInt(CustomerReview::getTasteRating).average();
        OptionalDouble priceRatingAverage = customerReviews.stream().mapToInt(CustomerReview::getPriceRating).average();

        restaurant.setServiceRatingAverage(serviceRatingAverage.orElse(0.0));
        restaurant.setTasteRatingAverage(tasteRatingAverage.orElse(0.0));
        restaurant.setPriceRatingAverage(priceRatingAverage.orElse(0.0));

        restaurantRepository.save(restaurant);
    }

}
