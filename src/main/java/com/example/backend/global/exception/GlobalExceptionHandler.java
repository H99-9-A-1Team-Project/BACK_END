package com.example.backend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 기본 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleApiRequestException(Exception ex) {
        if (ex.getMessage().contains("SQL"))
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SQL_CONFLICT), HttpStatus.CONFLICT);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()); // 나중에 Custom Error Code로 변경
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    // 400대 에러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

}


