package com.start.ecom.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private int userId;
    private String accessToken;
    private String refreshToken;
}
