package com.example.backend.domain;

import lombok.Getter;

@Getter
public enum MemberRole {
    MEMBER(Authority.MEMBER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    MemberRole(String authority){
        this.authority = authority;}

    public static class Authority{
        private static final String MEMBER = "ROLE_MEMBER";
        private static final String ADMIN = "ROLE_ADMIN";
    }

}
