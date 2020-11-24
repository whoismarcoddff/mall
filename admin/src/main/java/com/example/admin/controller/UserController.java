package com.example.admin.controller;

import com.example.admin.config.security.JwtTokenUtil;
import com.example.admin.domain.dto.UserRegisterRequest;
import com.example.admin.domain.dto.UserLoginRequest;
import com.example.admin.domain.dto.UserView;
import com.example.admin.domain.entity.User;
import com.example.admin.domain.mapper.UserViewMapper;
import com.example.admin.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {
    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userService;

    private final UserViewMapper userViewMapper;

    private final Logger logger;

    public UserController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, UserViewMapper userViewMapper, Logger logger) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userViewMapper = userViewMapper;
        this.logger = logger;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) throws Exception {

        logger.info("username ---> {}", request.getUsername());
        logger.info("password ---> {}", request.getPassword());

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            final User user = userService.loadUserByUsername(request.getUsername());
            UserView userView = userViewMapper.userToUserView(user);

            final String token = jwtTokenUtil.generateToken(user, request);

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(userView);

        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register")
    public UserView register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }


    @GetMapping("/users")
    public List<UserView> getAll() {
        List<UserView> userViews = userViewMapper.userToUserView(userService.getAll());
        return userViews;
    }

    @GetMapping("/users/{userId}")
    public UserView findById(@PathVariable("userId") int userId) {
        Optional<User> result = userService.findById(userId);
        if (!result.isPresent()) {
            throw new RuntimeException("Invalid userId " + userId);
        }
        UserView userView = userViewMapper.userToUserView(result.get());
        return userView;
    }

    @PutMapping("/users/{userId}")
    public UserView update(@PathVariable int userId, @RequestBody User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
        if (!userService.existsById(userId)) {
            throw new RuntimeException("User id " + userId + " not exist");
        }
        user.setId(userId);
        UserView userView = userViewMapper.userToUserView(userService.saveOrUpdate(user));
        return userView;
    }

    @PostMapping("/users")
    public UserView create(@RequestBody User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
        user.setId(0);
        UserView userView = userViewMapper.userToUserView(userService.saveOrUpdate(user));
        return userView;
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable int userId) {
        if (!userService.existsById(userId)) {
            throw new RuntimeException("User id provided not exist");
        }
        userService.deleteById(userId);
    }
}
