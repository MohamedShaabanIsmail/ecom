package com.start.ecom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.start.ecom.DTOs.LoginRequest;
import com.start.ecom.DTOs.LoginResponse;
import com.start.ecom.models.Users;
import com.start.ecom.repositores.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users addUser(Users user) throws Exception {
        if(userRepo.findByEmail(user.getEmail()).isPresent()){
            throw new Exception("User already exists");
        }
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public LoginResponse verifyUser(LoginRequest user) throws Exception {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(auth.isAuthenticated()){
            Users logedUser = userRepo.findByEmail(user.getEmail()).get();

            LoginResponse response = new LoginResponse();
            response.setUserId(logedUser.getId());
            response.setAccessToken(jwtService.generateToken(logedUser.getUsername(), logedUser.getEmail(), 1000*60*30, true));
            response.setRefreshToken(jwtService.generateToken(logedUser.getUsername(), logedUser.getEmail(), 1000*60*60*24*7, false));

            return response;
        }
        return null;
    }

    public LoginResponse refreshTokens(String refreshToken) throws Exception {

        String email = jwtService.extractEmail(refreshToken, false);
        Users user = userRepo.findByEmail(email).orElseThrow(() -> new Exception("User not found"));

        if(jwtService.validateToken(refreshToken, user.getUsername(), false)){
            LoginResponse response = new LoginResponse();
            response.setUserId(user.getId());
            response.setAccessToken(jwtService.generateToken(user.getUsername(), user.getEmail(), 1000*60*30, true));
            response.setRefreshToken(refreshToken);

            return response;
        }
        return null;
    }

    public Users updateUser(int id, Users user) throws Exception {
        if(userRepo.existsById(id)){
            return userRepo.save(user);
        }
        throw new Exception("User not found");
    }
    
}
