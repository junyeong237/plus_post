package com.example.plus_assignment.domain.user.exception;


import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class UnauthenticatedAuthCodeEmailException extends RestApiException {

    public UnauthenticatedAuthCodeEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
