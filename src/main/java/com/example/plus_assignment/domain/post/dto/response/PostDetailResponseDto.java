package com.example.plus_assignment.domain.post.dto.response;


import com.example.plus_assignment.domain.post.entity.Post;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponseDto {

    String title;
    String content;

    //List<Comment> commentList = new ArrayList<>();

    @Builder
    public PostDetailResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
    }

}
