package com.example.backend.global.exception.customexception.common;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class InvalidInputValue extends CustomException {
    public InvalidInputValue() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}