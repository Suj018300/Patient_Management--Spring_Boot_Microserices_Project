package com.example.auth_service.services;

import com.example.auth_service.dtos.UserDto;

public interface AuthService {

    UserDto registerUser (UserDto userDto);

    //promote guest user to admin user role
    String promoteToAdmin(String email);

}
