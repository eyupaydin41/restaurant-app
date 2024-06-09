package com.restaurant.restaurantservice.service;

import com.restaurant.restaurantservice.dto.RestaurantRequest;
import com.restaurant.restaurantservice.dto.RestaurantResponse;
import com.restaurant.restaurantservice.exception.ResourceNotFoundException;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public void createRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantRequest.getName())
                .category(restaurantRequest.getCategory())
                .address(restaurantRequest.getAddress())
                .phone(restaurantRequest.getPhone())
                .email(restaurantRequest.getEmail())
                .description(restaurantRequest.getDescription())
                .build();

        restaurantRepository.save(restaurant);
    }

    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::EntityToDto).collect(Collectors.toList());
    }


    public RestaurantResponse getRestaurantByID(Long id) {
        return restaurantRepository.findById(id).map(this::EntityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }


    public void updateRestaurant(Long id, RestaurantRequest restaurantRequest) {

        restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

        Restaurant restaurant = Restaurant.builder()
                .id(id)
                .name(restaurantRequest.getName())
                .description(restaurantRequest.getDescription())
                .email(restaurantRequest.getEmail())
                .phone(restaurantRequest.getPhone())
                .category(restaurantRequest.getCategory())
                .address(restaurantRequest.getAddress())
                .build();

        restaurantRepository.save(restaurant);

    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.findById(id).ifPresentOrElse(restaurantRepository::delete,
                () -> {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        );

    }

    private RestaurantResponse EntityToDto(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantResponse.class);
    }

}
