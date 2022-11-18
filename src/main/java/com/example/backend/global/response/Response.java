package com.example.backend.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) //  null 값을 가지는 필드는, JSON 응답에 포함되지 않음
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Response {
    private String result;
    private int code;
    private Result check;

    public static Response success() {
        return new Response("Success!", 200,null);
    }

    public static <T> Response success(T data) { // 5
        return new Response("Success!", 200, new Success<>(data));
    }

    public static Response failure(int code, String msg) { // 6
        return new Response("Fail..", code, new Failure(msg));
    }

}