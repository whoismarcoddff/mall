package com.example.backend.filter;

import com.example.backend.common.constant.SecurityConstants;
import com.example.backend.component.OrderCancelTask;
import com.example.backend.utils.JwtTokenUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, StringRedisTemplate stringRedisTemplate) {
        super(authenticationManager);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;

        System.out.println(jwtTokenUtils);


        if (token != null && token.startsWith(SecurityConstants.TOKEN_PREFIX) && jwtTokenUtils.validateToken(token)) {
            authentication = JwtTokenUtils.getAuthentication(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

    }
}
