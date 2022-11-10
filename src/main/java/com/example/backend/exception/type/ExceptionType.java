package com.example.backend.exception.type;

import lombok.Getter;

@Getter
public enum ExceptionType {
    EXCEPTION("exception.code", "exception.msg"),
    AUTHENTICATION_ENTRY_POINT_EXCEPTION("authenticationEntryPointException.code", "authenticationEntryPointException.msg"),
    ACCESS_DENIED_EXCEPTION("accessDeniedException.code", "accessDeniedException.msg"),
    BIND_EXCEPTION("bindException.code", "bindException.msg"),
    LOGIN_FAILURE_EXCEPTION("loginFailureException.code", "loginFailureException.msg"),
    MEMBER_EMAIL_ALREADY_EXISTS_EXCEPTION("memberEmailAlreadyExistsException.code", "memberEmailAlreadyExistsException.msg"),
    MEMBER_NOT_FOUND_EXCEPTION("memberNotFoundException.code", "memberNotFoundException.msg"),
    ROLE_NOT_FOUND_EXCEPTION("roleNotFoundException.code", "roleNotFoundException.msg"),
    MISSING_REQUEST_HEADER_EXCEPTION("missingRequestHeaderException.code", "missingRequestHeaderException.msg"),
    CANNOT_CONVERT_NESTED_STRUCTURE_EXCEPTION("cannotConvertNestedStructureException.code", "cannotConvertNestedStructureException.msg"),
    POST_NOT_FOUND_EXCEPTION("postNotFoundException.code", "postNotFoundException.msg"),
    COMMENT_NOT_FOUND_EXCEPTION("commentNotFoundException.code", "commentNotFoundException.msg"),
    REFRESH_TOKEN_FAILURE_EXCEPTION("refreshTokenFailureException.code", "refreshTokenFailureException.msg");

    private final String code;
    private final String message;

    ExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
