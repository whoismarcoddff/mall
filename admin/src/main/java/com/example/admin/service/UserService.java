package com.example.admin.service;

import com.example.admin.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    Optional<User> findById(int id);

    User saveOrUpdate(User user);

    void deleteById(int id);

    Boolean existsById(int id);
}
