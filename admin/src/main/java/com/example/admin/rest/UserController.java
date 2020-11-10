package com.example.admin.rest;

import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

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
