package com.example.backend.user.exception.register;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class EmailTooLargeException extends CustomException {
    public EmailTooLargeException() {
        super(ErrorCode.EMAIL_TOO_LARGE);
    }
}
