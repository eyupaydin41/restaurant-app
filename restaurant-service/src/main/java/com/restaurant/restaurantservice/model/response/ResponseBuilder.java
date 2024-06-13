package com.restaurant.restaurantservice.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {
    public ResponseEntity<Response> buildResponse(String message, HttpStatus status, Object data) {
        Response response = new Response(status, message, data);
        return ResponseEntity.status(status).body(response);
    }

    public ResponseEntity<Response> buildResponse(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }
}
