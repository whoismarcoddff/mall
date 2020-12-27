package com.example.backend.service;

import com.example.backend.dto.request.UserLoginRequest;

public interface AuthService {
    public String getAccessToken(UserLoginRequest loginRequest);
}
