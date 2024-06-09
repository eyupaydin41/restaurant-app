package com.restaurant.restaurantservice.controller;

import com.restaurant.restaurantservice.dto.RestaurantRequest;
import com.restaurant.restaurantservice.dto.RestaurantResponse;
import com.restaurant.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        restaurantService.createRestaurant(restaurantRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        restaurantService.updateRestaurant(id,restaurantRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public RestaurantResponse findRestaurantByID(@PathVariable Long id) {
        return restaurantService.getRestaurantByID(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }





}