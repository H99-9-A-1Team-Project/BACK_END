package com.example.backend.global.exception.customexception;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class AccessDeniedException extends CustomException {
    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED_EXCEPTION);
    }
}