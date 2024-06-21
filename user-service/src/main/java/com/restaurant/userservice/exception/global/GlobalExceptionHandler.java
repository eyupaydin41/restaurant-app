package com.restaurant.userservice.exception.global;

import com.restaurant.userservice.exception.ConstraintException;
import com.restaurant.userservice.exception.DuplicateUserException;
import com.restaurant.userservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintException.class)
    public ResponseEntity<?> handleConstraintException(ConstraintException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getErrors(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<?> handleDuplicateUserException(DuplicateUserException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.CONFLICT);
    }


}
