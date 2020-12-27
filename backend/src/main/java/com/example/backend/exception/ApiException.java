package com.example.backend.exception;

import com.example.backend.dto.response.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
