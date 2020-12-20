package com.example.backend.service;
import com.example.backend.model.dto.UserLoginRequest;

public interface AuthService {
    public String getAccessToken(UserLoginRequest loginRequest);

    public void deleteTokenFromRedis();
}
