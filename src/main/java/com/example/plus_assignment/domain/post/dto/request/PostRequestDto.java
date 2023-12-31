package com.example.plus_assignment.domain.post.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

    String title;
    @Size(max = 5000, message = "5000자 까지만 입력가능합니다.")
    String content;

    @Builder//테스트용
    private PostRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
