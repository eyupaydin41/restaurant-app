package com.restaurant.restaurantservice.service;

import com.restaurant.restaurantservice.dto.RestaurantDTO;
import com.restaurant.restaurantservice.dto.response.RestaurantResponse;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.model.response.Response;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {

    ResponseEntity<Response> createRestaurant(RestaurantDTO restaurantDTO);
    ResponseEntity<Response> getAllRestaurants();
    ResponseEntity<Response> getRestaurantByID(Long id);
    ResponseEntity<Response> updateRestaurant(Long id, RestaurantDTO restaurantDTO);
    ResponseEntity<Response> deleteRestaurant(Long id);
    RestaurantResponse entityToDto(Restaurant restaurant);

}
