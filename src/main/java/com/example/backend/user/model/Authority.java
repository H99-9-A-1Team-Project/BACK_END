package com.example.backend.user.model;

import lombok.Getter;

@Getter
public enum Authority {
    ROLE_USER(0),
    ROLE_REALTOR(1),
    ROLE_ADMIN(2);

    private int num;
    Authority(int num) {
        this.num = num;
    }
}
