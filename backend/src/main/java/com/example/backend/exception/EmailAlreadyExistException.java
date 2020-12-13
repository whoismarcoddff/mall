package com.example.backend.exception;

import java.util.Map;

public class EmailAlreadyExistException extends BaseException{

    public EmailAlreadyExistException(Map<String, Object> data) {
        super(ErrorCode.EMAIL_ALREADY_EXIST, data);
    }
}
