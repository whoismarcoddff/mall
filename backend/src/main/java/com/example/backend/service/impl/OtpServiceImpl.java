package com.example.backend.service.impl;

import com.example.backend.service.OtpService;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class OtpServiceImpl implements OtpService {
    private Random random = new Random();
    private final Map<String, Integer> otpCache;

    public OtpServiceImpl(Map<String, Integer> otpCache) {
        this.otpCache = otpCache;
    }

    @Override
    public int generateOTP(String key) throws ExecutionException {
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }


    @Override
    public int getOTP(String key) {
        try {
            int otp = otpCache.get(key);
            return otp;
        } catch (Exception ex) {
            System.out.println("ooo otp ex: " + ex.getMessage());
            return 0;
        }
    }

    @Override
    public void clearOTP(String key) {

    }
}
