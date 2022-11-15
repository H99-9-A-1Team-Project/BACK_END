package com.example.backend.global.exception.customexception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class PremiseNotFoundException extends CustomException {
    public PremiseNotFoundException() {
        super(ErrorCode.PREMISE_NOT_FOUND);
    }
}
