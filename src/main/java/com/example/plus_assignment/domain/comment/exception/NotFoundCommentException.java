package com.example.plus_assignment.domain.comment.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class NotFoundCommentException extends RestApiException {

    public NotFoundCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
