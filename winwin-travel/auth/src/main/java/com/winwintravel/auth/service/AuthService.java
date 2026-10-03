package com.winwintravel.auth.service;

import com.winwintravel.auth.config.JwtTokenUtil;
import com.winwintravel.auth.dto.JwtResponse;
import com.winwintravel.auth.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(UserDto userDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        UserDetails userDetails = userService.loadUserByUsername(userDto.getEmail());
        return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));
    }

    public ResponseEntity<?> createNewUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userService.findByUsername(userDto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is created");
        }
        userService.createNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
