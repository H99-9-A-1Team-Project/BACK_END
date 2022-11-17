package com.example.backend.global.entity;

public enum ApproveState {
    APPROVE_WAIT(0),
    APPROVE_COMPLETE(1),
    APPROVE_REJECT(2);
    private int ApproveNum;
    ApproveState(int num) {
        this.ApproveNum = num;
    }
}
