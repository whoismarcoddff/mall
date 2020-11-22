package com.example.admin.controller;

import com.example.admin.config.security.JwtTokenUtil;
import com.example.admin.domain.dto.UserRegisterRequest;
import com.example.admin.domain.dto.UserLoginRequest;
import com.example.admin.domain.dto.UserView;
import com.example.admin.domain.entity.User;
import com.example.admin.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    private final Logger logger;

    public UserController(Logger logger) {
        this.logger = logger;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) throws Exception {

        logger.info("username ---> {}", request.getUsername());
        logger.info("password ---> {}", request.getPassword());

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            final User user = userService.loadUserByUsername(request.getUsername());

            final String token = jwtTokenUtil.generateToken(user);

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(user);

        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register")
    public UserView register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }


    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    public User findById(@PathVariable("userId") int userId) {
        Optional<User> result = userService.findById(userId);
        if (!result.isPresent()) {
            throw new RuntimeException("Invalid userId " + userId);
        }
        return result.get();
    }

    @PutMapping("/users/{userId}")
    public User update(@PathVariable int userId, @RequestBody User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
        if (!userService.existsById(userId)) {
            throw new RuntimeException("User id " + userId + " not exist");
        }
        user.setId(userId);
        return userService.saveOrUpdate(user);
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
        user.setId(0);
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable int userId) {
        if (!userService.existsById(userId)) {
            throw new RuntimeException("User id provided not exist");
        }
        userService.deleteById(userId);
    }
}
