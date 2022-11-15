package com.example.backend.global.exception.customexception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class ExceedMaximumConsult extends CustomException {
    public ExceedMaximumConsult() {
        super(ErrorCode.EXCEED_MAXIMUM_CONSULT);
    }
}