package com.example.plus_assignment.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReRequestDto {
    @Size(max = 100)
    @NotBlank(message = "댓글은 공백이 불가능합니다.")
    private String content;

    private Long parentId;


    @Builder
    private CommentReRequestDto(String content,Long parentId){
        this.content = content;
        this.parentId = parentId;
    }
}
