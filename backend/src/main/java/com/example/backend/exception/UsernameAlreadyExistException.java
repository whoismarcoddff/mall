package com.example.backend.exception;

import java.util.Map;

public class UsernameAlreadyExistException extends BaseException{

    public UsernameAlreadyExistException(Map<String, Object> data) {

        super(ErrorCode.USERNAME_ALREADY_EXIST, data);
    }
}
