package com.example.backend.service.Impl;

import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import com.example.backend.utils.CurrentUserUtils;
import com.example.backend.utils.JwtTokenUtils;
import com.example.backend.model.dto.UserLoginRequest;
import com.example.backend.model.entity.JwtUser;
import com.example.backend.model.entity.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;

    private final CurrentUserUtils currentUserUtils;

    public AuthServiceImpl(UserService userService, StringRedisTemplate stringRedisTemplate, CurrentUserUtils currentUserUtils) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.currentUserUtils = currentUserUtils;
    }

    @Override
    public String getAccessToken(UserLoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (!userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("The user name or password is not correct.");
        }
        JwtUser jwtUser = new JwtUser(user);
        //TODO: handle isEnabled
        if (!jwtUser.isEnabled()) {
            throw new BadCredentialsException("User is forbidden to log in");
        }
        List<String> authorities = jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = JwtTokenUtils.createAccessToken(user.getUsername(), user.getId().toString(), authorities, loginRequest.getRememberMe());

        return token;
    }


}
