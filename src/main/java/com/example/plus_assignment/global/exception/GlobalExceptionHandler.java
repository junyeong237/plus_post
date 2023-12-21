package com.example.plus_assignment.global.exception;


import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(RestApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ErrorResponse response = ErrorResponse.builder()
            .code(errorCode.name())
            .status(errorCode.getHttpStatus().value())
            .build();
        response.addMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(response);


    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {


        ArrayList<String> errors = new ArrayList<>();
        e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

//
//        e.getBindingResult().getFieldErrors().stream()
//            .forEach(
//                error -> errors.add(error.getDefaultMessage())
//            );
        ErrorCode errorCode = CommonErrorCode.VALIDATION_ERROR;
        ErrorResponse response = ErrorResponse.builder()
            .messages(errors)
            .code(errorCode.name())
            .status(errorCode.getHttpStatus().value())
            .build();

        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(response);

    }




}
