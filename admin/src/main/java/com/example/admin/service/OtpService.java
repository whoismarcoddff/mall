package com.example.admin.service;

import java.util.concurrent.ExecutionException;

public interface OtpService {
    public int generateOTP(String key) throws ExecutionException;
    public int getOTP(String key);
    public void clearOTP(String key);
}
