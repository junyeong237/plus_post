package com.example.plus_assignment.domain.post.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    FORBIDDEN_ACCESS(HttpStatus.BAD_REQUEST, "작성자만 게시글를 수정/삭제할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
