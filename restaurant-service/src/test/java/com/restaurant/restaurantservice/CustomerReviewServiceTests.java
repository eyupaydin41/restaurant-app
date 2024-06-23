package com.restaurant.restaurantservice;

import com.restaurant.restaurantservice.dto.CustomerReviewDTO;
import com.restaurant.restaurantservice.dto.DeleteReviewRequest;
import com.restaurant.restaurantservice.exception.*;
import com.restaurant.restaurantservice.model.CustomerReview;
import com.restaurant.restaurantservice.model.Restaurant;
import com.restaurant.restaurantservice.model.User;
import com.restaurant.restaurantservice.model.response.Response;
import com.restaurant.restaurantservice.model.response.ResponseBuilderA;
import com.restaurant.restaurantservice.repository.CustomerReviewRepository;
import com.restaurant.restaurantservice.repository.RestaurantRepository;
import com.restaurant.restaurantservice.repository.UserRepository;
import com.restaurant.restaurantservice.service.impl.CustomerReviewServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerReviewServiceTests {

    @Mock
    private CustomerReviewRepository customerReviewRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ResponseBuilderA responseBuilderA;

    @InjectMocks
    private CustomerReviewServiceImpl customerReviewService;

    private Restaurant restaurant;
    private CustomerReviewDTO customerReviewDTO;
    private CustomerReview customerReview;
    private User user;
    private DeleteReviewRequest deleteReviewRequest;
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setCustomerReviews(new ArrayList<>());

        customerReviewDTO = new CustomerReviewDTO();
        customerReviewDTO.setId(1L);
        customerReviewDTO.setUserId(1L);
        customerReviewDTO.setRestaurantId(1L);
        customerReviewDTO.setServiceRating(5);
        customerReviewDTO.setTasteRating(4);
        customerReviewDTO.setPriceRating(3);
        customerReviewDTO.setComment("Great restaurant!");

        customerReview = new CustomerReview();
        customerReview.setId(1L);
        customerReview.setServiceRating(5);
        customerReview.setTasteRating(4);
        customerReview.setPriceRating(3);
        customerReview.setComment("Great restaurant!");
        customerReview.setRestaurant(restaurant);
        customerReview.setUserId(1L);

        deleteReviewRequest = new DeleteReviewRequest();
        deleteReviewRequest.setReviewId(1L);
        deleteReviewRequest.setUserId(1L);

    }

    @Test
    void createReview_ShouldReturnCreatedResponse() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(customerReviewRepository.findByUserIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(customerReviewRepository.save(any(CustomerReview.class))).thenReturn(customerReview);
        when(responseBuilderA.buildResponse(HttpStatus.CREATED)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        ResponseEntity<Response> response = customerReviewService.createReview(customerReviewDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(customerReviewRepository, times(1)).save(any(CustomerReview.class));
        verify(responseBuilderA, times(1)).buildResponse(HttpStatus.CREATED);
    }

    @Test
    void createReview_ShouldThrowDuplicateReviewException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(customerReviewRepository.findByUserIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(customerReview));

        assertThrows(DuplicateReviewException.class, () -> customerReviewService.createReview(customerReviewDTO));

        verify(customerReviewRepository, never()).save(any(CustomerReview.class));
    }

    @Test
    void createReview_ShouldThrowUserNotFoundException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> customerReviewService.createReview(customerReviewDTO));

        verify(customerReviewRepository, never()).save(any(CustomerReview.class));
    }

    @Test
    void createReview_ShouldThrowRestaurantNotFoundException() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> customerReviewService.createReview(customerReviewDTO));

        verify(customerReviewRepository, never()).save(any(CustomerReview.class));
    }

    @Test
    void getAllReviews_ShouldReturnReviewList() {
        when(customerReviewRepository.findAll()).thenReturn(Collections.singletonList(customerReview));
        when(modelMapper.map(any(CustomerReview.class), eq(CustomerReviewDTO.class))).thenReturn(customerReviewDTO);
        when(responseBuilderA.buildResponse(anyString(), eq(HttpStatus.OK), anyList())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = customerReviewService.getAllReviews();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerReviewRepository, times(1)).findAll();
    }

    @Test
    void getReviewByID_ShouldReturnReview() {
        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.of(customerReview));
        when(modelMapper.map(any(CustomerReview.class), eq(CustomerReviewDTO.class))).thenReturn(customerReviewDTO);
        when(responseBuilderA.buildResponse(anyString(), eq(HttpStatus.OK), any(CustomerReviewDTO.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = customerReviewService.getReviewByID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerReviewRepository, times(1)).findById(anyLong());
    }

    @Test
    void getReviewByID_ShouldThrowReviewNotFoundException() {
        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> customerReviewService.getReviewByID(1L));

        verify(customerReviewRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateReview_ShouldReturnOkResponse() {
        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.of(customerReview));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(responseBuilderA.buildResponse(HttpStatus.OK)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = customerReviewService.updateReview(customerReviewDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerReviewRepository, times(1)).save(any(CustomerReview.class));
    }

    @Test
    void updateReview_ShouldThrowReviewNotFoundException() {
        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> customerReviewService.updateReview(customerReviewDTO));

        verify(customerReviewRepository, never()).save(any(CustomerReview.class));
    }

    @Test
    void updateReview_ShouldThrowUserNotFoundException() {
        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.of(customerReview));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> customerReviewService.updateReview(customerReviewDTO));

        verify(customerReviewRepository, never()).save(any(CustomerReview.class));
    }

    @Test
    void updateReview_ShouldThrowUnauthorizedOperationException() {
        User user2 = User.builder()
                .id(3L)
                .build();

        customerReviewDTO.setUserId(user2.getId());

        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.of(customerReview));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user2));

        assertThrows(UnauthorizedOperationException.class, () -> customerReviewService.updateReview(customerReviewDTO));

        verify(customerReviewRepository, never()).save(any(CustomerReview.class));

    }

    @Test
    void deleteReview_ShouldReturnOkResponse() {
        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.of(customerReview));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(responseBuilderA.buildResponse(HttpStatus.OK)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = customerReviewService.deleteReview(deleteReviewRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerReviewRepository, times(1)).delete(any(CustomerReview.class));
    }

    @Test
    void deleteReview_ShouldThrowReviewNotFoundException() {
        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> customerReviewService.deleteReview(deleteReviewRequest));

        verify(customerReviewRepository, never()).delete(any(CustomerReview.class));
    }

    @Test
    void deleteReview_ShouldThrowUnauthorizedOperationException() {
        User user2 = User.builder()
                .id(3L)
                .build();

        deleteReviewRequest.setUserId(user2.getId());

        when(customerReviewRepository.findById(anyLong())).thenReturn(Optional.of(customerReview));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user2));

        assertThrows(UnauthorizedOperationException.class, () -> customerReviewService.deleteReview(deleteReviewRequest));

        verify(customerReviewRepository, never()).save(any(CustomerReview.class));

    }

}
