package com.example.backend.service;

import java.util.concurrent.ExecutionException;

public interface OtpService {
    public String generateOtp(String key) throws ExecutionException;
    public boolean verifyOtp(String key, String value);
}
