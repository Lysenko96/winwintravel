package com.winwintravel.auth.controller;

import com.winwintravel.auth.dto.JwtResponse;
import com.winwintravel.auth.dto.UserDto;
import com.winwintravel.auth.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Getter
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private String token;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return authService.createNewUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        ResponseEntity<?> response = authService.createAuthToken(userDto);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        this.token = jwtResponse != null ? jwtResponse.getToken() : null;
        return response;
    }

}
