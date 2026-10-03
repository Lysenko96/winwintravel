package com.winwintravel.auth.config;

import com.winwintravel.auth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration lifetime;

    private final UserRepository userRepository;

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
        claims.put("roles", roles);
        Date issued = new Date();
        Date expired = new Date(issued.getTime() + lifetime.toMillis());
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(issued)
                .expiration(expired)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
