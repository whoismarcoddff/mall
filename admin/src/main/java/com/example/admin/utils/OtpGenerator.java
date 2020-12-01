package com.example.admin.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpGenerator {

    private static final Integer EXPIRE_MIN = 5;
    private LoadingCache<String, Integer> otpCache;

    private final HashMap<String, Integer> otpStore = new HashMap<>();

    /**
     * Constructor configuration.
     */
    public OtpGenerator() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public Integer generateOTP(String key) {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);

        otpStore.put(key, OTP);
//        otpCache.put(key, OTP);


        System.out.println("ooo generateOTP1 " + otpStore.get(key));
        System.out.println("ooo generateOTP2 " + otpStore.get(key));
        return OTP;
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public Integer getOPTByKey(String key) {
        System.out.println("ooo getOTO otpStore " + otpStore);
        System.out.println("ooo getOTO otpStore otp " + otpStore.get(key));
        try {
            if (otpStore.containsKey(key)) {
                return otpStore.get(key);
            }
            return -1;
//            return otpCache.get(key);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Method for removing key from cache.
     *
     * @param key - target key
     */
    public void clearOTPFromCache(String key) {
        otpStore.remove(key);
//        otpCache.invalidate(key);
    }
}
