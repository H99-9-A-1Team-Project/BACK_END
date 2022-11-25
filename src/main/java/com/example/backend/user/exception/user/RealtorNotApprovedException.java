package com.example.backend.user.exception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class RealtorNotApprovedException extends CustomException {
    public RealtorNotApprovedException() {
        super(ErrorCode.REALTOR_NOT_APPROVED);
    }
}
