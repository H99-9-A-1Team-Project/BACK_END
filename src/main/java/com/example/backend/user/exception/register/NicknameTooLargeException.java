package com.example.backend.user.exception.register;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class NicknameTooLargeException extends CustomException {
    public NicknameTooLargeException() {
        super(ErrorCode.NICKNAME_TOO_LARGE);
    }
}
