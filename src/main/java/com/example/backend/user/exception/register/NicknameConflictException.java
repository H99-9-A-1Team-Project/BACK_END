package com.example.backend.user.exception.register;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class NicknameConflictException extends CustomException {
    public NicknameConflictException() {
        super(ErrorCode.NICKNAME_CONFLICT);
    }
}
