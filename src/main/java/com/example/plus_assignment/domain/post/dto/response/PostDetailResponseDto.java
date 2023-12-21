package com.example.plus_assignment.domain.post.dto.response;


import com.example.plus_assignment.domain.comment.dto.response.CommentResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponseDto {

    String title;
    String content;

    List<CommentResponseDto> commentList = new ArrayList<>();

    @Builder
    public PostDetailResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.commentList = post.getCommentList().stream()
            .map(CommentResponseDto::new).toList();
    }

}
