package com.example.plus_assignment.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation에러입니다. 입력값의 조건을 확인해주세요");

    private final HttpStatus httpStatus;
    private final String message;

}
