package com.example.auth_service.dtos;

public record LoginRequest(
        String email,
        String password
) {
}
