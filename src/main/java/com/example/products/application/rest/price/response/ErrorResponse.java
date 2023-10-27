package com.example.products.application.rest.price.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(
        HttpStatus status,
        LocalDateTime timestamp,
        String message,
        List<String> errors
) {
}
