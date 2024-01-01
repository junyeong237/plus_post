package com.example.plus_assignment.domain.comment.controller;


import com.example.plus_assignment.domain.comment.dto.request.CommentReRequestDto;
import com.example.plus_assignment.domain.comment.dto.request.CommentRequestDto;
import com.example.plus_assignment.domain.comment.dto.response.CommentReResponseDto;
import com.example.plus_assignment.domain.comment.dto.response.CommentResponseDto;
import com.example.plus_assignment.domain.comment.service.impl.CommentServiceImpl;
import com.example.plus_assignment.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/{postId}")
    public CommentResponseDto createComment(
        @PathVariable Long postId,
        @Valid @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentResponseDto responseDto = commentService.createComment(postId,requestDto, userDetails.getUser());
        return responseDto;
    }
    @PostMapping("/{postId}/re")
    public CommentReResponseDto createReComment(
        @PathVariable Long postId,
        @Valid @RequestBody CommentReRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        CommentReResponseDto responseDto = commentService.createReComment(postId,requestDto,userDetails.getUser());
        return responseDto;

    }

    @GetMapping()
    public Page<CommentResponseDto> getCommentList(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam(value = "sortBy",required = false) String sortBy,
        @RequestParam("isAsc") boolean isAsc

    ){

        Page<CommentResponseDto> commentResponseDto = commentService.getComments(page-1,size,sortBy,isAsc);
        return  commentResponseDto;
    }

    @PutMapping("/{commentId}")
    public CommentResponseDto updateComment(
        @Valid @RequestBody CommentRequestDto requestDto,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentResponseDto responseDto = commentService.updateComment(requestDto, commentId, userDetails.getUser());
        return responseDto;
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.deleteComment(commentId, userDetails.getUser());
    }

}
