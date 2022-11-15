package com.example.backend.global.exception.customexception.register;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class EmailTooLargeException extends CustomException {
    public EmailTooLargeException() {
        super(ErrorCode.EMAIL_TOO_LARGE);
    }
}