package com.example.backend.global.exception.customexception.consult;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class ConsultNotFoundException extends CustomException {
    public ConsultNotFoundException() {
        super(ErrorCode.CONSULT_NOT_FOUND);
    }
}
