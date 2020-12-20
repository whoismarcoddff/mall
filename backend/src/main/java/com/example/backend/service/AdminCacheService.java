package com.example.backend.service;

public interface AdminCacheService {
    void setOtp(String email, String otp);

    String getOtp(String email);
}
