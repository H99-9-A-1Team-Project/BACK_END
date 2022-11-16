package com.example.backend.global.entity;

public enum AnswerState {
    ROLE_WAIT(0),
    ROLE_ANSWER(1),
    ROLE_FINISH(2);
    private int AnswerNum;
    AnswerState(int num) {
        this.AnswerNum = num;
    }
}
