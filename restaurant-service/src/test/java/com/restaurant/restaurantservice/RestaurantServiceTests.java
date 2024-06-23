package com.restaurant.restaurantservice;

import com.restaurant.restaurantservice.dto.DeleteRestaurantRequest;
import com.restaurant.restaurantservice.dto.RestaurantDTO;
import com.restaurant.restaurantservice.dto.response.RestaurantResponse;
import com.restaurant.restaurantservice.dto.response.RestaurantWithReviewsResponse;
import com.restaurant.restaurantservice.exception.RestaurantNotFoundException;
import com.restaurant.restaurantservice.exception.UnauthorizedOperationException;
import com.restaurant.restaurantservice.exception.UserNotFoundException;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.model.RestaurantCategory;
import com.restaurant.restaurantservice.model.User;
import com.restaurant.restaurantservice.model.UserType;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.model.response.ResponseBuilderA;
import com.restaurant.restaurantservice.repository.RestaurantRepository;
import com.restaurant.restaurantservice.repository.UserRepository;
import com.restaurant.restaurantservice.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ResponseBuilderA responseBuilderA;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private RestaurantDTO restaurantDTO;
    private Restaurant restaurant;
    private User user;
    private DeleteRestaurantRequest deleteRestaurantRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserType(UserType.MANAGER);
        user.setRestaurantList(new ArrayList<>()); // Boş liste ile başlatılıyor


        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(1L);
        restaurantDTO.setUserId(1L);
        restaurantDTO.setName("Test Restaurant");
        restaurantDTO.setCategory(RestaurantCategory.PIZZACI);
        restaurantDTO.setAddress("123 Main St");
        restaurantDTO.setPhone("123-456-7890");
        restaurantDTO.setEmail("test@restaurant.com");
        restaurantDTO.setDescription("A test restaurant");

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setCategory(RestaurantCategory.PIZZACI);
        restaurant.setAddress("123 Main St");
        restaurant.setPhone("123-456-7890");
        restaurant.setEmail("test@restaurant.com");
        restaurant.setDescription("A test restaurant");
        restaurant.setUser(user);

        deleteRestaurantRequest = new DeleteRestaurantRequest();
        deleteRestaurantRequest.setUserId(1L);
        deleteRestaurantRequest.setRestaurantId(1L);
    }

    @Test
    void createRestaurant_ShouldReturnCreatedResponse() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        when(responseBuilderA.buildResponse(HttpStatus.CREATED)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        ResponseEntity<Response> response = restaurantService.createRestaurant(restaurantDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void createRestaurant_ShouldThrowUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> restaurantService.createRestaurant(restaurantDTO));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void createRestaurant_ShouldThrowUnauthorizedOperationException() {
        user.setUserType(UserType.STANDARD);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(UnauthorizedOperationException.class, () -> restaurantService.createRestaurant(restaurantDTO));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void getAllRestaurants_ShouldReturnRestaurantList() {
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));
        when(modelMapper.map(any(Restaurant.class), eq(RestaurantResponse.class))).thenReturn(new RestaurantResponse());
        when(responseBuilderA.buildResponse(anyString(), eq(HttpStatus.OK), any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = restaurantService.getAllRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void getRestaurantByID_ShouldReturnRestaurant() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(modelMapper.map(any(Restaurant.class), eq(RestaurantWithReviewsResponse.class))).thenReturn(new RestaurantWithReviewsResponse());
        when(responseBuilderA.buildResponse(anyString(), eq(HttpStatus.OK), any(RestaurantWithReviewsResponse.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = restaurantService.getRestaurantByID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurantRepository, times(1)).findById(anyLong());
    }

    @Test
    void getRestaurantByID_ShouldThrowRestaurantNotFoundException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.getRestaurantByID(1L));

        verify(restaurantRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateRestaurant_ShouldReturnOkResponse() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(responseBuilderA.buildResponse(HttpStatus.OK)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = restaurantService.updateRestaurant(restaurantDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_ShouldThrowRestaurantNotFoundException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.updateRestaurant(restaurantDTO));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_ShouldThrowUserNotFoundException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> restaurantService.updateRestaurant(restaurantDTO));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_ShouldThrowUnauthorizedOperationException_WhenUserIsStandard() {
        user.setUserType(UserType.STANDARD);
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(UnauthorizedOperationException.class, () -> restaurantService.updateRestaurant(restaurantDTO));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_ShouldThrowUnauthorizedOperationException_WhenUserIsNotOwner() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setUserType(UserType.MANAGER);

        restaurantDTO.setUserId(2L);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(anotherUser));

        assertThrows(UnauthorizedOperationException.class, () -> restaurantService.updateRestaurant(restaurantDTO));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void deleteRestaurant_ShouldReturnOkResponse() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(responseBuilderA.buildResponse(HttpStatus.OK)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = restaurantService.deleteRestaurant(deleteRestaurantRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurantRepository, times(1)).delete(any(Restaurant.class));
    }

    @Test
    void deleteRestaurant_ShouldThrowRestaurantNotFoundException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.deleteRestaurant(deleteRestaurantRequest));

        verify(restaurantRepository, never()).delete(any(Restaurant.class));
    }

    @Test
    void deleteRestaurant_ShouldThrowUserNotFoundException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> restaurantService.deleteRestaurant(deleteRestaurantRequest));

        verify(restaurantRepository, never()).delete(any(Restaurant.class));
    }

    @Test
    void deleteRestaurant_ShouldThrowUnauthorizedOperationException_WhenUserIsNotManager() {
        user.setUserType(UserType.STANDARD);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(UnauthorizedOperationException.class, () -> restaurantService.deleteRestaurant(deleteRestaurantRequest));

        verify(restaurantRepository, never()).delete(any(Restaurant.class));
    }
}
