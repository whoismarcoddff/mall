package com.example.backend.service.impl;

import com.example.backend.service.OtpService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {
    private static final Integer EXPIRE_MINS = 5;
    private Random random = new Random();

    private LoadingCache<String, Integer> otpCache;

    public OtpServiceImpl(Map<String, Integer> otpStore){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                return 0;
            }
        });
    }

    @Override
    public int generateOTP(String key) throws ExecutionException {

        int otp = 100000 + random.nextInt(900000);

        otpCache.put(key, otp);

        System.out.println("ooo generateOTP: " + otpCache.get(key));

        return otp;
    }


    @Override
    public int getOTP(String key) {
        try {
            System.out.println("ooo getOTP: " + otpCache.get(key));
            return otpCache.get(key);
        } catch (Exception ex) {
            System.out.println("ooo otp ex" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public void clearOTP(String key) {
        otpCache.invalidate(key);
//        otpStore.remove(key);
    }
}
