package com.restaurant.userservice.exception.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public ErrorDetails(int statusCode, List<String> messageList, String details) {
        this.statusCode = statusCode;
        this.messageList = messageList;
        this.details = details;
    }


}