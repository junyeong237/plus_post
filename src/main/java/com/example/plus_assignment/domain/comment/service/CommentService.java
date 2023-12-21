package com.example.plus_assignment.domain.comment.service;

import com.example.plus_assignment.domain.comment.dto.request.CommentRequestDto;
import com.example.plus_assignment.domain.comment.dto.response.CommentResponseDto;
import com.example.plus_assignment.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface CommentService {

    CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user);

    CommentResponseDto updateComment(CommentRequestDto requestDto, Long commentId, User user);

    void deleteComment(Long commentId, User user);

    Page<CommentResponseDto> getComments(int i, int size, String sortBy, boolean isAsc);
}
