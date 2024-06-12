package com.restaurant.restaurantservice.service;

import com.restaurant.restaurantservice.dto.RestaurantRequest;
import com.restaurant.restaurantservice.dto.RestaurantResponse;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.model.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {

    void createRestaurant(RestaurantRequest restaurantRequest);
    List<RestaurantResponse> getAllRestaurants();
    RestaurantResponse getRestaurantByID(Long id);
    void updateRestaurant(Long id, RestaurantRequest restaurantRequest);
    void deleteRestaurant(Long id);
    RestaurantResponse EntityToDto(Restaurant restaurant);

}
