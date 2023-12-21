package com.example.plus_assignment.domain.user.exception;


import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class VerifyPasswordException extends RestApiException {

    public VerifyPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
