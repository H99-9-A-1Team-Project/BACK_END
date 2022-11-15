package com.example.backend.global.exception.customexception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class RealtorNotApprovedYetException extends CustomException {
    public RealtorNotApprovedYetException() {
        super(ErrorCode.REALTOR_NOT_APPROVED_YET);
    }
}
