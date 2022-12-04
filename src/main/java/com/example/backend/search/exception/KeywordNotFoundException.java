package com.example.backend.search.exception;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class KeywordNotFoundException extends CustomException {
    public KeywordNotFoundException() {
        super(ErrorCode.KEYWORD_NOT_FOUND);
    }
}
