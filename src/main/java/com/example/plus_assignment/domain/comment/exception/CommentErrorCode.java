package com.example.plus_assignment.domain.comment.exception;

import com.example.plus_assignment.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    NOT_FOUND_Comment(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    FORBIDDEN_ACCESS_COMMENT(HttpStatus.BAD_REQUEST, "작성자만 댓글을 수정/삭제할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
