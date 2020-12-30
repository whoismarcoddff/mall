package com.example.backend.security;

import com.example.backend.constant.SecurityConstants;
import com.example.backend.service.AdminCacheService;
import com.example.backend.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    @Autowired
    private AdminCacheService adminCacheService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;

        // check token format
        if (token != null && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            String tokenValue = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String id = JwtTokenUtils.getIdFromToken(tokenValue);
            LOGGER.info("Checking user id: " + id);
            // check token validity
            if (id != null && JwtTokenUtils.validateToken(tokenValue) && (adminCacheService.getLogout(id) == null || !adminCacheService.getLogout(id).equals(tokenValue))) {
                authentication = JwtTokenUtils.getAuthentication(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("Authenticated user id: " + id);
                chain.doFilter(request, response);
                return;
            }
        }

        LOGGER.info("Invalid token");
        SecurityContextHolder.clearContext();
        chain.doFilter(request, response);
    }
}
