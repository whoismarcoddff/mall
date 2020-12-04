package com.example.admin.service;

import com.example.admin.domain.dto.UserRegisterRequest;
import com.example.admin.domain.dto.UserView;
import com.example.admin.domain.entity.User;
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
