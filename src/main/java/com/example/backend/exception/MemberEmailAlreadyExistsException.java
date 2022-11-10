package com.example.backend.exception;

public class MemberEmailAlreadyExistsException extends RuntimeException{
    public MemberEmailAlreadyExistsException(String message) {
        super(message);
    }
}
