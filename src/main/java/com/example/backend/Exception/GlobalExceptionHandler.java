package com.example.backend.Exception;

import com.example.backend.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected  ResponseEntity<ResponseDto<Object>> handlingGlobalException(GlobalException e){
        return new ResponseEntity<>(ResponseDto.fail(
                e.getErrorCode().getHttpStatus(),
                e.getErrorCode().getMessage(),
                e.getErrorCode().getDetail()),
                HttpStatus.valueOf(e.get().getHttpStatus()));
    }
}