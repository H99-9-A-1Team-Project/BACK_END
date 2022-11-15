package com.example.backend.global.exception.customexception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class UserUnauthorizedException extends CustomException {
    public UserUnauthorizedException() {
        super(ErrorCode.USER_UNAUTHORIZED);
    }
}
