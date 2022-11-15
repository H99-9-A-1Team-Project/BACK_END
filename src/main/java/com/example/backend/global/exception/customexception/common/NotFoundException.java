package com.example.backend.global.exception.customexception.common;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
