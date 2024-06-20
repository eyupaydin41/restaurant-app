package com.restaurant.restaurantservice.dto;

import com.restaurant.restaurantservice.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private UserType userType;
}
