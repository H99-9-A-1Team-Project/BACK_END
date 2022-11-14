package com.example.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {


    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "올바르지 않은 값입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C002", "잘못된 요청입니다."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "C003", "접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"C004", "알 수 없는 오류가 발생하였습니다."),
    SQL_CONFLICT(HttpStatus.CONFLICT, "C006", "중복된 값이 존재합니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "C007", "페이지를 찾을 수 없습니다."),

    // User
    TOKEN_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "U001" , "토큰이 유효하지 않습니다."),
    LOGIN_FAIL_EXCEPTION(HttpStatus.UNAUTHORIZED, "U002", "로그인에 실패했습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND ,"U003","존재하지 않는 회원입니다."),
    MEMBER_NOT_EQUALS_EXCEPTION(HttpStatus.BAD_REQUEST ,"U004" ,"회원 정보가 일치하지 않습니다." ),
    MEMBER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT,"U005" , "이미 존재하는 회원입니다."),

    // ?
    ALREADY_EXISTS_EXCEPTION(HttpStatus.CONFLICT, "C005", "이미 값이 존재합니다.");




    private HttpStatus status;
    private String code;
    private String message;


    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
