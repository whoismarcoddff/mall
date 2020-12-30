package com.example.backend.controller;

import com.example.backend.dto.response.CommonResult;
import com.example.backend.exception.Asserts;
import com.example.backend.dto.request.OtpVerifyRequest;
import com.example.backend.dto.request.UserRegisterRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.model.User;
import com.example.backend.dto.mapper.UserViewMapper;
import com.example.backend.dto.response.UserView;
import com.example.backend.service.MailService;
import com.example.backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserViewMapper userViewMapper;
    private final UserService userService;
    private final MailService mailService;

    public UserController(UserViewMapper userViewMapper, UserService userService, MailService mailService) {
        this.userViewMapper = userViewMapper;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    @ResponseBody
    public CommonResult signUp(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        User user = userService.save(userRegisterRequest);
        if (user == null) {
            return CommonResult.failed();
        }
        return CommonResult.success("Register success");
    }

    @GetMapping("/requestOtp")
    @ResponseBody
    public CommonResult requestOtp(@RequestParam String email) {
        String otp = userService.generateOtp(email);
        try {
            mailService.sendSimpleEmai(email, "One-Time Password Validation", String.format("Your one-time passcode is %s", otp));
        } catch (Exception e) {
            Asserts.fail("Sending Email failed");
        }
        return CommonResult.success("Request otp successfully");
    }

    @PostMapping("/verifyOtp")
    @ResponseBody
    public CommonResult verifyOtp(@RequestBody OtpVerifyRequest request) {
        if (!userService.verifyOtp(request.getOtp(), request.getEmail())) {
            return CommonResult.failed("Otp verify failed");
        }
        return CommonResult.success("Otp verify successfully");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ResponseBody
    public CommonResult getAllUser(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<UserView> allUser = userService.getAll(pageNum, pageSize);
        return CommonResult.success(allUser);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseBody
    public CommonResult update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        int count = userService.update(userUpdateRequest);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> deleteUserByUserName(@RequestParam("username") String username) {
        userService.delete(username);
        return ResponseEntity.ok().build();
    }
}
