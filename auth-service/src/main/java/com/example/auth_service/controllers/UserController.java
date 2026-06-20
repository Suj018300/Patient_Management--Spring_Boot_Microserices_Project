package com.example.auth_service.controllers;


import com.example.auth_service.config.AppConstants;
import com.example.auth_service.dtos.UserDto;
import com.example.auth_service.services.AuthService;
import com.example.auth_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userDto));
    }


    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_ADMIN')")
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(Authentication authentication) {

        System.out.println(authentication);

        return ResponseEntity.ok("VALID accessToken");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/validate-admin")
    public ResponseEntity<String> validateAdminToken(Authentication authentication) {

        System.out.println(authentication);

        return ResponseEntity.ok("VALID ADMIN TOKEN");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getUser () {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email ) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId) {
        return ResponseEntity.ok(userService.updateUser(userDto, userId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserId(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }


}
