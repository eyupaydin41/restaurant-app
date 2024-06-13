package com.restaurant.userservice.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {
    public ResponseEntity<Response> buildResponse(String message, HttpStatus status, Object data) {
        Response response = new Response(status, message, data);
        return ResponseEntity.status(status).body(response);
    }
}
