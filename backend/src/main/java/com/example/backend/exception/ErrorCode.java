package com.example.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USERNAME_ALREADY_EXIST(1001, HttpStatus.BAD_REQUEST, "Username already exists"),
    EMAIL_ALREADY_EXIST(1001, HttpStatus.BAD_REQUEST, "Email already exists"),
    ROLE_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "Role not found"),
    USERNAME_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "Username not found"),
    EMAIL_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "Email not found"),
    VERIFY_JWT_FAILED(1003, HttpStatus.UNAUTHORIZED, "Verify token failed"),
    METHOD_ARGUMENT_NOT_VALID(1003, HttpStatus.BAD_REQUEST, "Method argument not valid");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
