package com.example.backend.exception;

import com.example.backend.common.result.ErrorCode;

public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(ErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
