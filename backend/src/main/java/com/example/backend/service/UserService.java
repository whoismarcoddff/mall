package com.example.backend.service;

import com.example.backend.dto.response.CommonResult;
import com.example.backend.dto.request.UserRegisterRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.model.User;
import com.example.backend.dto.response.UserView;
import org.springframework.data.domain.Page;


public interface UserService {
    public User save(UserRegisterRequest userRegisterRequest);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public int update(UserUpdateRequest userUpdateRequest);

    public void delete(String email);

    public Page<UserView> getAll(int pageNum, int pageSize);

    public boolean checkPassword(String currentPassword, String password);

    public String generateOtp(String email);

    public boolean verifyOtp(String otp, String email);

}
