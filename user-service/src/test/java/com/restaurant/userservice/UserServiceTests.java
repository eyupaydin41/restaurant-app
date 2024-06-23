package com.restaurant.userservice;

import com.restaurant.userservice.dto.UserDTO;
import com.restaurant.userservice.dto.response.UserResponse;
import com.restaurant.userservice.exception.DuplicateUserException;
import com.restaurant.userservice.exception.UserNotFoundException;
import com.restaurant.userservice.model.User;
import com.restaurant.userservice.model.UserType;
import com.restaurant.userservice.model.response.Response;
import com.restaurant.userservice.model.response.ResponseBuilderA;
import com.restaurant.userservice.repository.UserRepository;
import com.restaurant.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ResponseBuilderA responseBuilderA;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");
        userDTO.setUserType(UserType.MANAGER);

        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setUserType(UserType.MANAGER);
    }

    @Test
    void createUser_ShouldReturnCreatedResponse() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(responseBuilderA.buildResponse(HttpStatus.CREATED)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        ResponseEntity<Response> response = userService.createUser(userDTO);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowDuplicateUserException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(DuplicateUserException.class, () -> userService.createUser(userDTO));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(responseBuilderA.buildResponse(anyString(), eq(HttpStatus.OK), any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = userService.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByID_ShouldReturnUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(new UserResponse());
        when(responseBuilderA.buildResponse(anyString(), eq(HttpStatus.OK), any(UserResponse.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = userService.getUserByID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getUserByID_ShouldThrowUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByID(1L));

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateUser_ShouldReturnOkResponse() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(responseBuilderA.buildResponse(HttpStatus.OK)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = userService.updateUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldThrowUserNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldReturnOkResponse() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(responseBuilderA.buildResponse(HttpStatus.OK)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Response> response = userService.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void deleteUser_ShouldThrowUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(3L));

        verify(userRepository, never()).delete(any(User.class));
    }
}
