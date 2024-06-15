package com.restaurant.restaurantservice.service.impl;

import com.restaurant.restaurantservice.dto.RestaurantDTO;
import com.restaurant.restaurantservice.dto.response.RestaurantResponse;
import com.restaurant.restaurantservice.dto.response.RestaurantWithReviewsResponse;
import com.restaurant.restaurantservice.exception.RestaurantNotFoundException;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.model.response.ResponseBuilder;
import com.restaurant.restaurantservice.repository.RestaurantRepository;
import com.restaurant.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final ResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<Response> createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDTO.getName())
                .category(restaurantDTO.getCategory())
                .address(restaurantDTO.getAddress())
                .phone(restaurantDTO.getPhone())
                .email(restaurantDTO.getEmail())
                .description(restaurantDTO.getDescription())
                .serviceRatingAverage(0.0)
                .priceRatingAverage(0.0)
                .tasteRatingAverage(0.0)
                .ratingAverage(0.0)
                .build();

        restaurantRepository.save(restaurant);

        return responseBuilder.buildResponse(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Response> getAllRestaurants() {
        List<RestaurantResponse> list = restaurantRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();

        String message = "The restaurant list has been successfully fetched.";

        return responseBuilder.buildResponse(message,HttpStatus.OK, list);
    }


    @Override
    public ResponseEntity<Response> getRestaurantByID(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        RestaurantWithReviewsResponse response = modelMapper.map(restaurant, RestaurantWithReviewsResponse.class);

        String message = "Restaurant found with id: " + id;

        return responseBuilder.buildResponse(message,HttpStatus.OK,response);
    }

    @Override
    public ResponseEntity<Response> updateRestaurant(Long id, RestaurantDTO restaurantDTO) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setEmail(restaurantDTO.getEmail());
        restaurant.setPhone(restaurantDTO.getPhone());
        restaurant.setCategory(restaurantDTO.getCategory());
        restaurant.setAddress(restaurantDTO.getAddress());

        restaurantRepository.save(restaurant);

        return responseBuilder.buildResponse(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Response> deleteRestaurant(Long id) {
        restaurantRepository.findById(id).ifPresentOrElse(restaurantRepository::delete,
                () -> {
                    throw new RestaurantNotFoundException(id);
                }
        );

        return responseBuilder.buildResponse(HttpStatus.OK);
    }

    @Override
    public RestaurantResponse entityToDto(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantResponse.class);
    }

}
