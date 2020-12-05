package com.example.admin.service.impl;

import com.example.admin.service.OtpService;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {

    @Override
    public int generateOTP(String key) throws ExecutionException {
        return 0;
    }

    @Override
    public int getOTP(String key) {
        return 0;
    }

    @Override
    public void clearOTP(String key) {

    }
}
