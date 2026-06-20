package com.example.auth_service.config;

public class AppConstants {

    public static final String[] AUTH_PUBLIC_URL = {
            "/v1/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };

    public static final String[] AUTH_ADMIN_URLS= {
            "/users/**",
            "/api/**"
    };

    public static final String[] AUTH_GUEST_URLS= {

    };

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String GUEST_ROLE = "GUEST";

}
