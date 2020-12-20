package com.example.backend.controller;

import com.example.backend.constant.SecurityConstants;
import com.example.backend.model.dto.UserLoginRequest;
import com.example.backend.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Api("Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ApiOperation("Login")
    public ResponseEntity<Void> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = authService.getAccessToken(userLoginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ApiOperation("Logout")
    public ResponseEntity<Void> logout() {
        authService.deleteTokenFromRedis();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
