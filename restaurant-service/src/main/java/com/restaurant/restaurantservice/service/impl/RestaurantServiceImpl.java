package com.restaurant.restaurantservice.service.impl;

import com.restaurant.restaurantservice.dto.DeleteRestaurantRequest;
import com.restaurant.restaurantservice.dto.RestaurantDTO;
import com.restaurant.restaurantservice.dto.response.RestaurantResponse;
import com.restaurant.restaurantservice.dto.response.RestaurantWithReviewsResponse;
import com.restaurant.restaurantservice.exception.RestaurantNotFoundException;
import com.restaurant.restaurantservice.exception.UnauthorizedOperationException;
import com.restaurant.restaurantservice.exception.UserNotFoundException;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.model.User;
import com.restaurant.restaurantservice.model.UserType;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.model.response.ResponseBuilderA;
import com.restaurant.restaurantservice.repository.RestaurantRepository;
import com.restaurant.restaurantservice.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ResponseBuilderA responseBuilderA;

    @Override
    public ResponseEntity<Response> createRestaurant(RestaurantDTO restaurantDTO) {

        User user = userRepository.findById(restaurantDTO.getUserId()).orElseThrow(
                () -> new UserNotFoundException(restaurantDTO.getUserId())
        );

        if (user.getUserType().equals(UserType.STANDARD)) {
            throw new UnauthorizedOperationException("The user not allowed to create restaurant");
        }

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDTO.getName())
                .user(user)
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

        user.getRestaurantList().add(restaurant);

        return responseBuilderA.buildResponse(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Response> getAllRestaurants() {
        List<RestaurantResponse> list = restaurantRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();

        String message = "The restaurant list has been successfully fetched.";

        return responseBuilderA.buildResponse(message, HttpStatus.OK, list);
    }


    @Override
    public ResponseEntity<Response> getRestaurantByID(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        RestaurantWithReviewsResponse response = modelMapper.map(restaurant, RestaurantWithReviewsResponse.class);

        String message = "Restaurant found with id: " + id;

        return responseBuilderA.buildResponse(message, HttpStatus.OK, response);
    }

    @Override
    public ResponseEntity<Response> updateRestaurant(RestaurantDTO restaurantDTO) {

        Restaurant restaurant = restaurantRepository.findById(restaurantDTO.getId())
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantDTO.getId()));

        User user = userRepository.findById(restaurantDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(restaurantDTO.getUserId()));

        if (user.getUserType().equals(UserType.STANDARD)) {
            throw new UnauthorizedOperationException("The user not allowed to update restaurant");
        }

        if (!restaurant.getUser().getId().equals(restaurantDTO.getUserId())) {
            throw new UnauthorizedOperationException("The user not allowed to update this restaurant.");
        }


        restaurant.setName(restaurantDTO.getName());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setEmail(restaurantDTO.getEmail());
        restaurant.setPhone(restaurantDTO.getPhone());
        restaurant.setCategory(restaurantDTO.getCategory());
        restaurant.setAddress(restaurantDTO.getAddress());

        restaurantRepository.save(restaurant);

        return responseBuilderA.buildResponse(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Response> deleteRestaurant(DeleteRestaurantRequest deleteRestaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(deleteRestaurantRequest.getRestaurantId()).orElseThrow(
                () -> new RestaurantNotFoundException(deleteRestaurantRequest.getRestaurantId())
        );

        User user = userRepository.findById(deleteRestaurantRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(deleteRestaurantRequest.getUserId()));

        if (!user.getUserType().equals(UserType.MANAGER)) {
            throw new UnauthorizedOperationException("The user not allowed to delete restaurant");
        }

        restaurantRepository.delete(restaurant);

        return responseBuilderA.buildResponse(HttpStatus.OK);
    }

    @Override
    public RestaurantResponse entityToDto(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantResponse.class);
    }
}
