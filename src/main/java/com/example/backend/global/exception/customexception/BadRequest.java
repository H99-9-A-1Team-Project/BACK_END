package com.example.backend.global.exception.customexception;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class BadRequest extends CustomException {
    public BadRequest() {
        super(ErrorCode.BAD_REQUEST);
    }
}