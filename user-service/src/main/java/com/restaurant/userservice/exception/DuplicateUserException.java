package com.restaurant.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException(String email) {
        super("There is already a user with the email address: " + email);
    }
}
