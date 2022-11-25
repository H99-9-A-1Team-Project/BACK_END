package com.example.backend.user.model;

public enum AccountCheck {
    APPROVE_WAIT(0),
    APPROVE_COMPLETE(1),
    APPROVE_REJECT(2);
    private int approveNum;
    AccountCheck(int approveNum) {
        this.approveNum = approveNum;
    }
}
