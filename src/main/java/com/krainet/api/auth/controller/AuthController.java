package com.krainet.api.auth.controller;

import com.krainet.api.auth.request.AuthRequest;
import com.krainet.api.auth.request.RegisterRequest;
import com.krainet.api.users.dto.UserDto;
import com.krainet.api.users.service.UserService;
import com.krainet.common.enums.UserRole;
import com.krainet.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtTokenProvider.createToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        UserDto userDto = UserDto.builder()
                .userName(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .role(UserRole.valueOf(registerRequest.getRole()))
                .build();

        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }
}