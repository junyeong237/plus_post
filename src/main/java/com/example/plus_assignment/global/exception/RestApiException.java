package com.example.plus_assignment.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
//@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private ErrorCode errorCode;
    private String password;
}
