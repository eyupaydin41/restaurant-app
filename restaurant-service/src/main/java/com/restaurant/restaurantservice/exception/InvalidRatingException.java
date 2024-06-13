package com.restaurant.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRatingException extends RuntimeException{
    public InvalidRatingException() {
        super("The rating must be between 1 and 10");
    }
}
