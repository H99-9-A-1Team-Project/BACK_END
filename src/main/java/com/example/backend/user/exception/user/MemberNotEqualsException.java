package com.example.backend.user.exception.user;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class MemberNotEqualsException extends CustomException {
    public MemberNotEqualsException() {
        super(ErrorCode.MEMBER_NOT_EQUALS_EXCEPTION);
    }
}
