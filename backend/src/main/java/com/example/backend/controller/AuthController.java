package com.example.backend.controller;

import com.example.backend.constant.SecurityConstants;
import com.example.backend.dto.mapper.UserViewMapper;
import com.example.backend.dto.request.UserLoginRequest;
import com.example.backend.dto.response.CommonResult;
import com.example.backend.dto.response.UserView;
import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@Api("Authentication")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserViewMapper userViewMapper;

    public AuthController(AuthService authService, UserService userService, UserViewMapper userViewMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userViewMapper = userViewMapper;
    }

    @PostMapping("/login")
    @ApiOperation("Login")
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response) {
        String token = authService.getAccessToken(userLoginRequest);
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);
        User user = userService.findByEmail(userLoginRequest.getEmail());
        UserView userView = userViewMapper.userToUserView(user);

        return CommonResult.success(userView,"Login success");
    }

    @PostMapping("/logout")
    @ApiOperation("Logout")
    public ResponseEntity<Void> logout() {
        //TODO: blacklist
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
