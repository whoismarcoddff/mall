package com.example.backend.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class OtpValidateRequest {
    @Email
    private String email;

    private int otp;
}
