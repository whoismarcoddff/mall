package com.example.backend.controller;

import com.example.backend.domain.dto.OtpValidateRequest;
import com.example.backend.service.impl.MailServiceImpl;
import com.example.backend.service.impl.OtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class OtpController {

    @Autowired
    private OtpServiceImpl otpService;

    private final MailServiceImpl mailService;

    public OtpController(MailServiceImpl mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/requestOTP")
    public void requestOTP(@RequestParam("email") String email) throws ExecutionException {
        String otp = otpService.generateOtp(email);
        mailService.sendSimpleEmai(email, "One-Time Password Validation", String.format("Your one-time passcode is %s", otp));
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity verifyOtp(@RequestBody OtpValidateRequest request) throws ExecutionException {
        String submitOtp = request.getOtp();
        String email = request.getEmail();
        boolean result = otpService.verifyOtp(email, submitOtp);

        if (result) {
            return ResponseEntity.ok().body("Otp is valid");
        } else {
            return ResponseEntity.badRequest().body("Otp is invalid");
        }
    }

}
