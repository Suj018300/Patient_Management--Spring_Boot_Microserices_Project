package com.example.auth_service.dtos;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String message,
        HttpStatus status,
        int statusCode
) {
}
