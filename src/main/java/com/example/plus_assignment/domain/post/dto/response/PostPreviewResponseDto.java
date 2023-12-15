package com.example.plus_assignment.domain.post.dto.response;

import com.example.plus_assignment.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPreviewResponseDto {

    String title;
    String content;

    @Builder
    public PostPreviewResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
