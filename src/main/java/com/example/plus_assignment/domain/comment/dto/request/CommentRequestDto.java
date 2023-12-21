package com.example.plus_assignment.domain.comment.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
    @Size(max = 100)
    @NotBlank(message = "댓글은 공백이 불가능합니다.")
    private String content;


    @Builder
    private CommentRequestDto(String content){
        this.content = content;
    }
}
