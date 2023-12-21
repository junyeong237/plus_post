package com.example.plus_assignment.domain.post.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class ForbiddenAccessPostException extends RestApiException {

    public ForbiddenAccessPostException(ErrorCode errorCode) {
        super(errorCode);
    }
}

