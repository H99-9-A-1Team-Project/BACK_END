package com.example.backend.user.exception.register;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class FormNotValidException extends CustomException {
    public FormNotValidException() {
        super(ErrorCode.FORM_NOT_VALID);
    }
}
