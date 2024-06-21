    package com.restaurant.restaurantservice.exception;

    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class UnauthorizedOperationException extends RuntimeException{
        public UnauthorizedOperationException(String message) {
            super(message);
        }
    }
