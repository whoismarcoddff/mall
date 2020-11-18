package com.example.admin.controller;

import com.example.admin.config.JwtTokenUtil;
import com.example.admin.entity.JwtRequest;
import com.example.admin.entity.JwtResponse;
import com.example.admin.entity.User;
import com.example.admin.entity.UserDTO;
import com.example.admin.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) throws Exception {

        System.out.println("ooo: " + request.getUsername());
        System.out.println("ooo: " + request.getPassword());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
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
