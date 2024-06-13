package com.restaurant.userservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class Response<T> {

    private HttpStatus status;
    private final String message;
    private final T data;

}
