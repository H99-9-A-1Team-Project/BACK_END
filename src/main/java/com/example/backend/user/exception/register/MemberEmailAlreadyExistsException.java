package com.example.backend.user.exception.register;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class MemberEmailAlreadyExistsException extends CustomException {
    public MemberEmailAlreadyExistsException() {
        super(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
    }
}
