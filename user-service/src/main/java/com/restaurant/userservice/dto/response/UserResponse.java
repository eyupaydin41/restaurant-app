package com.restaurant.userservice.dto.response;

import com.restaurant.userservice.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String surname;
    private String email;
    private String password;
    private UserType userType;
    private List<RestaurantResponse> restaurantList;
}
