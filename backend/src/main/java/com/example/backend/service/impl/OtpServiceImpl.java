package com.example.backend.service.impl;

import com.example.backend.service.OtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {
    private Random random = new Random();
    private final static long OTP_EXPIRE = 60 * 5;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Override
    public String generateOtp(String email) throws ExecutionException {
        String otp   = String.valueOf(100000 + random.nextInt(900000));
        setOperations.add(email, otp  );
        redisTemplate.expire(email, OTP_EXPIRE, TimeUnit.SECONDS);
        return otp  ;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        try {
            Set<String> otpCache = setOperations.members(email);
            return otpCache.contains(otp);
        } catch (Exception ex) {
            System.out.println("otp invalid: " + ex.getMessage());
            return false;
        }
    }
}
