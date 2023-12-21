package com.example.plus_assignment.domain.user.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    EXISTS_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 유저이름입니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, "인증 되지 않은 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호 확인번호가 일치하지 않습니다."),
    UNAUTHENTICATEDCODE_EMAIL(HttpStatus.UNAUTHORIZED, "인증 코드가 발급되지 않은 이메일입니다."),
    NOT_CORRECT_LOGIN_INPUT(HttpStatus.BAD_REQUEST, "닉네임 또는 패스워드를 확인해주세요.");

    private final HttpStatus httpStatus;
    private final String message;

}
