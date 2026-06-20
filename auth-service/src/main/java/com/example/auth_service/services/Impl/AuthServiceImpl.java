package com.example.auth_service.services.Impl;

import com.example.auth_service.dtos.UserDto;
import com.example.auth_service.entities.Role;
import com.example.auth_service.entities.User;
import com.example.auth_service.repositories.RoleRepository;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.AuthService;
import com.example.auth_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDto registerUser(UserDto userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userService.createUser(userDto);
    }

    @Override
    public String promoteToAdmin(String email) {
        // 1. Find the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // 2. Fetch the ADMIN role from DB
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not initialized in database."));

        // 3. Check if user already has the admin role to prevent duplicates
        if (user.getRoles().contains(adminRole)) {
            return "User is already an Admin.";
        }

        // 4. Add the Admin role (keeps existing roles like Guest/Normal intact)
        user.getRoles().add(adminRole);
        userRepository.save(user);

        return "User successfully promoted to ROLE_ADMIN.";
    };
}
