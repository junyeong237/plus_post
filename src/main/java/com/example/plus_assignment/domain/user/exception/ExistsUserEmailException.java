package com.example.plus_assignment.domain.user.exception;


import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class ExistsUserEmailException extends RestApiException {

    public ExistsUserEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
