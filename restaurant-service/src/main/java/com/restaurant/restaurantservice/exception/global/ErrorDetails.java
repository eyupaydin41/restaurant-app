package com.restaurant.restaurantservice.exception.global;

import lombok.Data;

import java.util.List;

@Data
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;
    private List<String> messageList;

    public ErrorDetails(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }


    public ErrorDetails(int statusCode, List<String> errors, String details) {
        this.statusCode = statusCode;
        this.messageList = errors;
        this.details = details;
    }
}