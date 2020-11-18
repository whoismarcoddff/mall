package com.example.admin.service;

import com.example.admin.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getAll();

    Optional<User> findById(int id);

    User saveOrUpdate(User user);

    void deleteById(int id);

    Boolean existsById(int id);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
