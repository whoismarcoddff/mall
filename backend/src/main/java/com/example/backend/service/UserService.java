package com.example.backend.service;

import com.example.backend.exception.*;
import com.example.backend.model.dto.UserRegisterRequest;
import com.example.backend.model.dto.UserUpdateRequest;
import com.example.backend.model.entity.Role;
import com.example.backend.model.entity.User;
import com.example.backend.model.entity.UserRole;
import com.example.backend.model.mapper.UserEditMapper;
import com.example.backend.model.mapper.UserViewMapper;
import com.example.backend.model.view.UserView;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserRoleRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    public static final String USERNAME = "username:";
    public static final String EMAIL = "email:";
    public static final String ROLE = "role:";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserEditMapper userEditMapper;
    private final UserViewMapper userViewMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, BCryptPasswordEncoder passwordEncoder, UserViewMapper userViewMapper, UserEditMapper userEditMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEditMapper = userEditMapper;
        this.userViewMapper = userViewMapper;
    }

    public void ensureUsernameNotExist(String username) {
        boolean present = userRepository.findByUsername(username).isPresent();
        if (present) {
            throw new UsernameAlreadyExistException(ImmutableMap.of(USERNAME, username));
        }
    }

    public void ensureEmailNotExist(String email) {
        boolean present = userRepository.findByEmail(email).isPresent();
        if (present) {
            throw new EmailAlreadyExistException(ImmutableMap.of(EMAIL, email));
        }
    }

    public void ensureRoleExist(String role) {
        boolean present = roleRepository.findByName(role).isPresent();
        if (!present) {
            throw new RoleNotFoundException(ImmutableMap.of(ROLE, role));
        }
    }

    public void save(UserRegisterRequest userRegisterRequest) {
        ensureEmailNotExist(userRegisterRequest.getEmail());
        ensureUsernameNotExist(userRegisterRequest.getUsername());
        ensureRoleExist(userRegisterRequest.getRole());

        User user = userEditMapper.requestToUser(userRegisterRequest);

        //TODO: emailVerified
        user.setIsEmailVerified(false);
        //TODO: enabled
        user.setIsEnabled(true);

        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        userRepository.save(user);

        Role role = roleRepository.findByName(userRegisterRequest.getRole()).get();
        userRoleRepository.save(new UserRole(user, role));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ImmutableMap.of(USERNAME, username)));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(ImmutableMap.of(USERNAME, email)));
    }

    public void update(UserUpdateRequest userUpdateRequest) {
        User user = findByEmail(userUpdateRequest.getEmail());
        String username = userUpdateRequest.getUsername();
        String password = userUpdateRequest.getPassword();
        String role = userUpdateRequest.getRole();

        if (Objects.nonNull(username)) {
            ensureUsernameNotExist(username);
            user.setUsername(username);
        }
        if (Objects.nonNull(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (Objects.nonNull(role)) {
            ensureRoleExist(role);
        }
        userRepository.save(user);
    }

    public void delete(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new EmailNotFoundException(ImmutableMap.of(EMAIL, email));
        }
        userRepository.deleteByEmail(email);
    }

    public Page<UserView> getAll(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize)).map(userViewMapper::userToUserView);
    }

    public boolean checkPassword(String currentPassword, String password) {
        return this.passwordEncoder.matches(currentPassword, password);
    }

}
