package com.example.backend.service.Impl;

import com.example.backend.cache.CacheException;
import com.example.backend.service.AdminCacheService;
import com.example.backend.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminCacheServiceImpl implements AdminCacheService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.expire.otp}")
    private Long REDIS_EXPIRE_OTP;
    @Value("${redis.key.otp}")
    private String REDIS_KEY_OTP;

    @CacheException
    @Override
    public void setOtp(String email, String otp) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_OTP + ":" + email;
        redisService.set(key, otp, REDIS_EXPIRE_OTP);
    }

    @CacheException
    @Override
    public String getOtp(String email) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_OTP + ":" + email;
        return (String) redisService.get(key);
    }
}
