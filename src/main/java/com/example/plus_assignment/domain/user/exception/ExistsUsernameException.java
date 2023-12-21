package com.example.plus_assignment.domain.user.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import com.example.plus_assignment.global.exception.RestApiException;

public class ExistsUsernameException extends RestApiException {

    public ExistsUsernameException(ErrorCode errorCode){
        super(errorCode);
    }

}
