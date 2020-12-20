package com.example.backend.aspect;

import com.example.backend.anotation.CacheException;
import com.example.backend.service.AdminCacheService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(2)
public class RedisCacheAspect {
    private static Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    //TODO: Improve it with a dynamic string for path modified
    @Pointcut("execution(public * com.example.backend.service.*CacheService.*(..))")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            if (method.isAnnotationPresent(CacheException.class)) {
                throw throwable;
            } else {
                logger.error(throwable.getMessage());
            }
        }
        return result;

    }



}
