package com.example.backend.controller;

import com.example.backend.config.security.JwtTokenUtil;
import com.example.backend.domain.dto.UserRegisterRequest;
import com.example.backend.domain.dto.UserLoginRequest;
import com.example.backend.domain.dto.UserView;
import com.example.backend.domain.entity.User;
import com.example.backend.domain.mapper.UserViewMapper;
import com.example.backend.service.impl.MailServiceImpl;
import com.example.backend.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final UserViewMapper userViewMapper;
    private final Logger logger;

    private final RedisTemplate<String, String> myRedisTemplate;

    private final SetOperations setOperations;

    public UserController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, MailServiceImpl mailService, UserServiceImpl userService, UserViewMapper userViewMapper, Logger logger,   RedisTemplate<String, String> myRedisTemplate) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userViewMapper = userViewMapper;
        this.logger = logger;
        this.myRedisTemplate = myRedisTemplate;
        this.setOperations = myRedisTemplate.opsForSet();
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

            setOperations.add("tokenId", token);


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
