package com.example.backend.service.impl;

import com.example.backend.domain.dto.UserRegisterRequest;
import com.example.backend.domain.dto.UserView;
import com.example.backend.domain.entity.User;
import com.example.backend.domain.mapper.UserEditMapper;
import com.example.backend.domain.mapper.UserViewMapper;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserViewMapper userViewMapper;
    private final UserEditMapper userEditMapper;
    private final MailServiceImpl mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserViewMapper userViewMapper, UserEditMapper userEditMapper, MailServiceImpl mailService) {
        this.userRepository = userRepository;
        this.userViewMapper = userViewMapper;
        this.userEditMapper = userEditMapper;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public List<User> getAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    @Override
    @Transactional
    public Optional<User> findById(int id) {
        Optional<User> result = userRepository.findById(id);
        return result;
    }

    @Override
    @Transactional
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserView register(UserRegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username" + request.getUsername() + "exists!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Password don't match!");
        }

        User user = userEditMapper.requestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        UserView userView = userViewMapper.userToUserView(user);


        return userView;
    }

    @Override
    @Transactional
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
