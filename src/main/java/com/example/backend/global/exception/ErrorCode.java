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
    NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "요청을 찾을 수 없습니다."),
    SQL_CONFLICT(HttpStatus.CONFLICT, "C006", "중복된 값이 존재합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"C007", "서버에서 오류가 발생했습니다."),

    // User
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "U001", "로그인 해주세요"),
    LOGIN_FAIL_EXCEPTION(HttpStatus.UNAUTHORIZED, "U002", "로그인에 실패했습니다."),
    REALTOR_NOT_APPROVED_YET(HttpStatus.FORBIDDEN, "U003", "공인중개사 인증이 완료되지 않았습니다."),
    REALTOR_NOT_APPROVED(HttpStatus.FORBIDDEN, "U004", "공인중개사 인증이 거부되었습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND ,"U005","존재하지 않는 회원입니다."),
    TOKEN_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "U006" , "토큰이 유효하지 않습니다."),
    MEMBER_NOT_EQUALS_EXCEPTION(HttpStatus.BAD_REQUEST ,"U007" ,"수정할 권한이 없습니다." ),

    // Register
    MEMBER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT,"R001" , "이미 존재하는 회원입니다."),
    NICKNAME_CONFLICT(HttpStatus.CONFLICT,"R002" , "이미 사용중인 닉네임입니다."),
    FORM_NOT_VALID(HttpStatus.BAD_REQUEST,"R003" , "유효하지 않은 요청 형식입니다"),
    NICKNAME_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "R004", "최대 닉네임 길이를 초과했습니다."),
    EMAIL_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "R005", "최대 이메일 길이를 초과했습니다."),

    // Consult
    EXCEED_MAXIMUM_CONSULT(HttpStatus.BAD_REQUEST, "CO01", "최대 상담 횟수를 초과했습니다."),

    // Premises
    PREMISE_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "존재하지 않는 공인중개사입니다.");



    private HttpStatus status;
    private String code;
    private String message;


    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
