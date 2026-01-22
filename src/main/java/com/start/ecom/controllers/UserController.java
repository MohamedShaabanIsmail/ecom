package com.start.ecom.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.start.ecom.DTOs.LoginRequest;
import com.start.ecom.DTOs.LoginResponse;
import com.start.ecom.models.Cart;
import com.start.ecom.models.Users;
import com.start.ecom.services.CartService;
import com.start.ecom.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/addUser")
    public ResponseEntity<?> register(@RequestBody Users user) {
        try {
            Cart cart = new Cart();
            cart.setUser(userService.addUser(user));
            cartService.create(cart);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verifyUser")
    public ResponseEntity<?> login(@RequestBody LoginRequest user, HttpServletResponse response) {
        try {
            LoginResponse loginResponse = userService.verifyUser(user);

            ResponseCookie cookie = ResponseCookie.from("refresh_Token", loginResponse.getRefreshToken())
            .httpOnly(true)
            .secure(false)
            // .sameSite("Strict")
            .path("/")
            .maxAge(7*24*60*60)
            .build();
            response.addHeader("Set-Cookie", cookie.toString());

            loginResponse.setRefreshToken(null);
            return ResponseEntity.ok().body(loginResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/renewal")
    public ResponseEntity<?> refreshTokens(@CookieValue("refresh_Token") String refreshToken, HttpServletResponse response) {
        try {
            if(!refreshToken.isBlank()){
                LoginResponse loginResponse = userService.refreshTokens(refreshToken);

                ResponseCookie cookie = ResponseCookie.from("refresh_Token", loginResponse.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                // .sameSite("Strict")
                .path("/")
                .maxAge(7*24*60*60)
                .build();
                response.addHeader("Set-Cookie", cookie.toString());

                loginResponse.setRefreshToken(null);
                return ResponseEntity.ok().body(loginResponse);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody Users user) {
        try {
            return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    
}
