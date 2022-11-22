package com.example.backend.global.entity;

public enum AnswerState {
    WAIT(0),
    ANSWER(1),
    FINISH(2);
    private int AnswerNum;
    AnswerState(int num) {
        this.AnswerNum = num;
    }
}
