package com.example.backend.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /* BAD_REQUEST 400 error*/
    BAD_PASSWORD(HttpStatus.BAD_REQUEST.value(), "Password incorrect", "비밀번호를 확인하세요"),

    /*UNAUTHORIZED 401 error*/

    /*Not Found 404 error*/

    /* CONFLICT 409 error*/
    BAD_PASSWORD_CONFIRM(HttpStatus.CONFLICT.value(), "Password and PasswordConfirm don't match", "비밀번호와 비밀번호 확인이 다릅니다."),
    DUPLICATE_MEMBER_ID(HttpStatus.CONFLICT.value(), "Member is duplicated", "중복된 사용자 ID가 존재합니다."),

    /*500 server error*/
    MEMBER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Member not found", "멤버를 찾을 수 없습니다.");

    private final Integer httpStatus;
    private final String message;
    private final String detail;
}
