package com.example.admin.controller;

import com.example.admin.domain.dto.OtpValidateRequest;
import com.example.admin.service.impl.MailServiceImpl;
import com.example.admin.service.impl.OtpServiceImpl;
import com.example.admin.utils.OtpGenerator;
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

        int otp = otpService.generateOTP(email);

        mailService.sendSimpleEmai(email, "One-Time Password Validation", String.format("Your one-time passcode is %s", otp));
    }

    @PostMapping("/validateOTP")
    public ResponseEntity validateOTP(@RequestBody OtpValidateRequest request) throws ExecutionException {
        int clientOtp = request.getOtp();
        String email = request.getEmail();
        int cacheOtp = otpService.getOTP(email);

        if (cacheOtp == clientOtp) {
            otpService.clearOTP(email);
            return ResponseEntity.ok().body("Otp is valid");
        } else {
            return ResponseEntity.badRequest().body("Otp is not valid");
        }
    }

}
