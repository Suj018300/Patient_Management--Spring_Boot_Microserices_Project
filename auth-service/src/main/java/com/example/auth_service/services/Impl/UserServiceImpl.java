package com.example.auth_service.services.Impl;

import com.example.auth_service.config.AppConstants;
import com.example.auth_service.dtos.UserDto;
import com.example.auth_service.entities.Provider;
import com.example.auth_service.entities.Role;
import com.example.auth_service.entities.User;
import com.example.auth_service.exceptions.ResourceNotFoundException;
import com.example.auth_service.helpers.UserHelper;
import com.example.auth_service.repositories.RoleRepository;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.security.JwtService;
import com.example.auth_service.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        if (userDto.getEmail()==null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exits");
        }
        User user = modelMapper.map(userDto, User.class);
        user.setProvider(userDto.getProvider()!=null ? userDto.getProvider() : Provider.LOCAL);

        Role role = roleRepository.findByName("ROLE_"+ AppConstants.GUEST_ROLE).orElse(null);
        user.getRoles().add(role);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given email id "));
        return modelMapper.map (user, UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User existingUser = userRepository
                .findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        //we are not going to change email id for this project.
        if (userDto.getName() != null) existingUser.setName(userDto.getName());
        if (userDto.getImage() != null) existingUser.setImage(userDto.getImage());
        if (userDto.getProvider() != null) existingUser.setProvider(userDto.getProvider());
        if (userDto.getPassword() != null) existingUser.setPassword(userDto.getPassword());
        existingUser.setEnable(userDto.isEnable());
        existingUser.setUpdatedAt(Instant.now());
        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }



//    @Override
//    public ResponseEntity<String> validateUser(String accessToken) {
//
//        try {
//            if (!jwtService.isAccessToken(accessToken)) {
//                //message pass kar hai---
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token type");
//            };
//
//            return ResponseEntity.ok().build();
//
//        } catch (ExpiredJwtException e) {
//            throw new BadCredentialsException("Token expired");
//
//        }
//    }

    @Override
    public void deleteUser(String userId) {
        UUID uuid = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found with give id"));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(UserHelper.parseUUID(userId)).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(
                        user -> modelMapper.map(user, UserDto.class)
                ).toList();
    }
}
