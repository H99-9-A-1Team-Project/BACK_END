package com.example.backend.global.exception.customexception;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class MemberNotFoundException extends CustomException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
