package com.example.backend.global.exception.customexception.register;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class NicknameTooLargeException extends CustomException {
    public NicknameTooLargeException() {
        super(ErrorCode.NICKNAME_TOO_LARGE);
    }
}
