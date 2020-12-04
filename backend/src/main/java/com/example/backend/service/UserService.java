package com.example.backend.service;

import com.example.backend.domain.dto.UserRegisterRequest;
import com.example.backend.domain.dto.UserView;
import com.example.backend.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getAll();

    Optional<User> findById(int id);

    User saveOrUpdate(User user);

    UserView register(UserRegisterRequest request);

    void deleteById(int id);

    Boolean existsById(int id);

    public User loadUserByUsername(String username) throws UsernameNotFoundException;
}
