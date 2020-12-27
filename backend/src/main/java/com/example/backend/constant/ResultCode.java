package com.example.backend.constant;

import com.example.backend.dto.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Common api result status
 */
@AllArgsConstructor
@Getter
public enum ResultCode implements ErrorCode {
    SUCCESS(200, "Success"),
    FAILED(500, "Failed"),
    VALIDATE_FAILED(404, "Invalid parameters"),
    UNAUTHORIZED(401, "Logged out or expired token"),
    FORBIDDEN(403, "Forbidden");

    private long code;
    private String message;
}
