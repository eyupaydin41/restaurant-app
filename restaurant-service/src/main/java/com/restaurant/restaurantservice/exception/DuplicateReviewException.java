package com.restaurant.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateReviewException extends RuntimeException{
    public DuplicateReviewException(String message) {
        super(message);
    }
}
