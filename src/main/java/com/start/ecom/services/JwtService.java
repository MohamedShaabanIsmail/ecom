package com.start.ecom.services;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey accesskey = Keys
            .hmacShaKeyFor(Decoders.BASE64.decode("Ashmo1RkS3djNXFZQWJZcVhWb0R0NGM1R2FHa1M0bVh6c0pYVnZOMUFzTg=="));

    private final SecretKey refreshKey = Keys
            .hmacShaKeyFor(Decoders.BASE64.decode("AshmoPROJECT0405jNzaFQwa2VuX1NlY3JldF9LZXlfMjAyNQ=="));

    public String generateToken(String username, String email, int time, boolean isAccessToken) {
        return Jwts.builder()
                .claims(new HashMap<>() {
                    {
                        put("username", username);
                        put("email", email);
                    }
                })
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + time))
                .signWith(isAccessToken ? accesskey : refreshKey)
                .compact();
    }

    private Claims extractAllClaims(String token, boolean isAccessToken) {
        return Jwts.parser().verifyWith(isAccessToken ? accesskey : refreshKey)
                .build().parseSignedClaims(token).getPayload();
    }

    public String extractEmail(String token,  boolean isAccessToken) {
        return extractAllClaims(token, isAccessToken).get("email", String.class);
    }

    private boolean isTokenExpired(String token, boolean isAccessToken) {
        return extractAllClaims(token, isAccessToken).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username, boolean isAccessToken){
        String tokenUsername = extractAllClaims(token, isAccessToken).get("username", String.class);
        return (tokenUsername.equals(username) && !isTokenExpired(token, isAccessToken));
    }
 }
