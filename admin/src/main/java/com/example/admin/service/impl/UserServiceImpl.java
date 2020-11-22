package com.example.admin.service.impl;

import com.example.admin.domain.dto.UserRegisterRequest;
import com.example.admin.domain.dto.UserView;
import com.example.admin.domain.entity.User;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> result = userRepository.findById(id);
        return result;
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserView register(UserRegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username" + request.getUsername() + "exists!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Password don't match!");
        }

        //TODO: UserEditMapper
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        //TODO: UserViewMapper
        UserView userView = new UserView();
        userView.setUsername(user.getUsername());
        userView.setEmail(user.getEmail());
        userView.setId(user.getId());
        return userView;
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(int id) {
        return userRepository.existsById(id);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("User " + username + "not found");
        }
        User user = optional.get();
        return user;
    }
}
