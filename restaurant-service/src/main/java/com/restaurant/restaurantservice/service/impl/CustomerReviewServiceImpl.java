package com.restaurant.restaurantservice.service.impl;

import com.restaurant.restaurantservice.dto.CustomerReviewDTO;
import com.restaurant.restaurantservice.dto.DeleteReviewRequest;
import com.restaurant.restaurantservice.exception.*;
import com.restaurant.restaurantservice.model.CustomerReview;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.model.response.ResponseBuilderA;
import com.restaurant.restaurantservice.repository.RestaurantRepository;
import com.restaurant.restaurantservice.repository.CustomerReviewRepository;
import com.restaurant.restaurantservice.repository.UserRepository;
import com.restaurant.restaurantservice.service.CustomerReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final CustomerReviewRepository customerReviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ResponseBuilderA responseBuilderA;

    @Override
    public ResponseEntity<Response> createReview(CustomerReviewDTO customerReviewDTO) {
        Restaurant restaurant = restaurantRepository.findById(customerReviewDTO.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(customerReviewDTO.getRestaurantId()));

        userRepository.findById(customerReviewDTO.getUserId())
                        .orElseThrow(() -> new UserNotFoundException(customerReviewDTO.getUserId()));

        customerReviewRepository.findByUserIdAndRestaurantId(customerReviewDTO.getUserId(), customerReviewDTO.getRestaurantId())
                .ifPresent((review) -> {
                    throw new DuplicateReviewException();
                });


        CustomerReview customerReview = CustomerReview.builder()
                .serviceRating(customerReviewDTO.getServiceRating())
                .tasteRating(customerReviewDTO.getTasteRating())
                .priceRating(customerReviewDTO.getPriceRating())
                .comment(customerReviewDTO.getComment())
                .restaurant(restaurant)
                .userId(customerReviewDTO.getUserId())
                .build();

        customerReviewRepository.save(customerReview);
        updateRestaurantRatings(restaurant);

        return responseBuilderA.buildResponse(HttpStatus.CREATED);

    }


    @Override
    public ResponseEntity<Response> getAllReviews() {
        List<CustomerReviewDTO> list = customerReviewRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();

        String message = "The customer review list has been successfully fetched.";
        return responseBuilderA.buildResponse(message, HttpStatus.OK, list);
    }

    @Override
    public ResponseEntity<Response> getReviewByID(Long id) {
        CustomerReviewDTO response = customerReviewRepository.findById(id)
                .map(this::entityToDto)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        String message = "Custoemr review found with id: " + id;

        return responseBuilderA.buildResponse(message, HttpStatus.OK, response);

    }

    @Override
    public ResponseEntity<Response> updateReview(CustomerReviewDTO customerReviewDTO) {

        CustomerReview customerReview = customerReviewRepository.findById(customerReviewDTO.getId())
                .orElseThrow(() -> new ReviewNotFoundException(customerReviewDTO.getId())); // yorum mevcut mu ?

        userRepository.findById(customerReviewDTO.getUserId())
                .orElseThrow( () -> new UserNotFoundException(customerReviewDTO.getUserId()) ); // user mevcut mu ?


        if (!customerReview.getUserId().equals(customerReviewDTO.getUserId())) { // güncelleyen user, yorumu yazan user mı ?
            throw new UnauthorizedOperationException("The user not allowed to update this review.");
        }

        customerReview.setServiceRating(customerReviewDTO.getServiceRating());
        customerReview.setTasteRating(customerReviewDTO.getTasteRating());
        customerReview.setPriceRating(customerReviewDTO.getPriceRating());
        customerReview.setComment(customerReviewDTO.getComment());

        customerReviewRepository.save(customerReview);
        updateRestaurantRatings(customerReview.getRestaurant());

        return responseBuilderA.buildResponse(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Response> deleteReview(DeleteReviewRequest deleteReviewRequest) {

        CustomerReview customerReview = customerReviewRepository.findById(deleteReviewRequest.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException(deleteReviewRequest.getReviewId()));

        userRepository.findById(deleteReviewRequest.getUserId())
                .orElseThrow( () -> new UserNotFoundException(deleteReviewRequest.getUserId()) );


        if (!deleteReviewRequest.getUserId().equals(customerReview.getUserId())) {
            throw new UnauthorizedOperationException("The user not allowed to delete this review.");
        }

        customerReviewRepository.delete(customerReview);
        updateRestaurantRatings(customerReview.getRestaurant());

        return responseBuilderA.buildResponse(HttpStatus.OK);

    }


    public CustomerReviewDTO entityToDto(CustomerReview customerReview) {
        return modelMapper.map(customerReview, CustomerReviewDTO.class);
    }

    private void updateRestaurantRatings(Restaurant restaurant) {
        List<CustomerReview> customerReviews = restaurant.getCustomerReviews();

        OptionalDouble serviceRatingAverage = customerReviews.stream().mapToInt(CustomerReview::getServiceRating).average();
        OptionalDouble tasteRatingAverage = customerReviews.stream().mapToInt(CustomerReview::getTasteRating).average();
        OptionalDouble priceRatingAverage = customerReviews.stream().mapToInt(CustomerReview::getPriceRating).average();
        OptionalDouble averageRating = customerReviews.stream().mapToDouble(CustomerReview::getAverageRating).average();

        restaurant.setServiceRatingAverage(roundToOneDecimalPlace(serviceRatingAverage.orElse(0.0)));
        restaurant.setTasteRatingAverage(roundToOneDecimalPlace(tasteRatingAverage.orElse(0.0)));
        restaurant.setPriceRatingAverage(roundToOneDecimalPlace(priceRatingAverage.orElse(0.0)));
        restaurant.setRatingAverage(roundToOneDecimalPlace(averageRating.orElse(0.0)));

        restaurantRepository.save(restaurant);
    }

    private Double roundToOneDecimalPlace(Double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
