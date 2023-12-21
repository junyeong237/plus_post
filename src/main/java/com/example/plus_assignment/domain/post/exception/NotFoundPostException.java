package com.example.plus_assignment.domain.post.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class NotFoundPostException extends RestApiException {

    public NotFoundPostException(ErrorCode errorCode) {
        super(errorCode);
    }
}
