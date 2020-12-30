package com.example.backend.controller;

import com.example.backend.constant.SecurityConstants;
import com.example.backend.dto.mapper.UserViewMapper;
import com.example.backend.dto.request.UserLoginRequest;
import com.example.backend.dto.response.CommonResult;
import com.example.backend.dto.response.UserView;
import com.example.backend.model.User;
import com.example.backend.security.JwtTokenUtils;
import com.example.backend.service.AdminCacheService;
import com.example.backend.service.AuthService;
import com.example.backend.service.RedisService;
import com.example.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@Api("Authentication")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserViewMapper userViewMapper;
    private final AdminCacheService adminCacheService;

    public AuthController(AuthService authService, UserService userService, UserViewMapper userViewMapper, RedisService redisService, AdminCacheService adminCacheService) {
        this.authService = authService;
        this.userService = userService;
        this.userViewMapper = userViewMapper;
        this.adminCacheService = adminCacheService;
    }

    @PostMapping("/login")
    @ApiOperation("Login")
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response) {
        String token = authService.getAccessToken(userLoginRequest);
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);
        User user = userService.findByEmail(userLoginRequest.getEmail());
        UserView userView = userViewMapper.userToUserView(user);

        return CommonResult.success(userView, "Login successful");
    }

    @PostMapping("/logout")
    @ApiOperation("Logout")
    public CommonResult logout() {
        String currentToken = JwtTokenUtils.getCurrentTokenByContext();
        if (currentToken != "") {
            Long expiredDateFromToken = JwtTokenUtils.getExpiredDateFromToken(currentToken).getTime();
            Long expiration = (expiredDateFromToken - new Date().getTime()) / 1000;
            String id = JwtTokenUtils.getIdFromToken(currentToken);
            adminCacheService.setLogout(id, currentToken, expiration);
        }

        return CommonResult.success("Logout successful");
    }
}
