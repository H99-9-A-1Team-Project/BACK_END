package com.example.backend.global.exception.customexception.common;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;

public class ImageNotFoundException extends CustomException {
    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
