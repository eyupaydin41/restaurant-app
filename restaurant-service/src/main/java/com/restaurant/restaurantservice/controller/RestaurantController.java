package com.restaurant.restaurantservice.controller;

import com.restaurant.restaurantservice.dto.DeleteRestaurantRequest;
import com.restaurant.restaurantservice.dto.RestaurantDTO;
import com.restaurant.restaurantservice.exception.ConstraintException;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.service.RestaurantService;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> findRestaurantByID(@PathVariable Long id) {
        return restaurantService.getRestaurantByID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> createRestaurant(@RequestBody @Valid RestaurantDTO restaurantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            throw new ConstraintException(errors);

        }
        return restaurantService.createRestaurant(restaurantDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> updateRestaurant(@RequestBody @Valid RestaurantDTO restaurantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            throw new ConstraintException(errors);

        }
        return restaurantService.updateRestaurant(restaurantDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> deleteRestaurant(@RequestBody DeleteRestaurantRequest deleteRestaurantRequest) {
        return restaurantService.deleteRestaurant(deleteRestaurantRequest);
    }

}