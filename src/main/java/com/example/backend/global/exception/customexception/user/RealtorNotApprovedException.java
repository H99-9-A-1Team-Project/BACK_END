package com.example.backend.global.exception.customexception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class RealtorNotApprovedException extends CustomException {
    public RealtorNotApprovedException() {
        super(ErrorCode.REALTOR_NOT_APPROVED);
    }
}
