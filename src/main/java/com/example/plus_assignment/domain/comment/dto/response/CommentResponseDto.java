package com.example.plus_assignment.domain.comment.dto.response;

import com.example.plus_assignment.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }

}
