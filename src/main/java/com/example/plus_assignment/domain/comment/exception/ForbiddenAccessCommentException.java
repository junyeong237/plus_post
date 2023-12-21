package com.example.plus_assignment.domain.comment.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class ForbiddenAccessCommentException extends RestApiException {

    public ForbiddenAccessCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}

