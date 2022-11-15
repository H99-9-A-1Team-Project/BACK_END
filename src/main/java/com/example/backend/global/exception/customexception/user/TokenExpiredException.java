package com.example.backend.global.exception.customexception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class TokenExpiredException extends CustomException {
    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED_EXCEPTION);
    }
}
