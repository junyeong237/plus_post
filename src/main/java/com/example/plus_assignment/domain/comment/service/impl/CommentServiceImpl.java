package com.example.plus_assignment.domain.comment.service.impl;

import com.example.plus_assignment.domain.comment.dto.request.CommentRequestDto;
import com.example.plus_assignment.domain.comment.dto.response.CommentResponseDto;
import com.example.plus_assignment.domain.comment.entity.Comment;
import com.example.plus_assignment.domain.comment.exception.CommentErrorCode;
import com.example.plus_assignment.domain.comment.exception.ForbiddenAccessCommentException;
import com.example.plus_assignment.domain.comment.exception.NotFoundCommentException;
import com.example.plus_assignment.domain.comment.repository.CommentRepository;
import com.example.plus_assignment.domain.comment.service.CommentService;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.exception.NotFoundPostException;
import com.example.plus_assignment.domain.post.exception.PostErrorCode;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.exception.NotFoundUserException;
import com.example.plus_assignment.domain.user.exception.UserErrorCode;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final UserRepositry userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {

        Post post = findPostById(postId);
        User Author = findUserById(user.getId());

        Comment comment = Comment.builder()
            .content(requestDto.getContent())
            .user(Author)
            .post(post)
            .build();

        commentRepository.save(comment);

        return null;
    }

    @Override
    public Page<CommentResponseDto> getComments(int page, int size, String sortBy,
        boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        if(sortBy == null || sortBy.isEmpty()){
            sortBy = "createdAt";
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Comment> commentList;

        commentList = commentRepository.getCommentList(pageable);
        return commentList.map(CommentResponseDto::new);
    }

    @Override
    public CommentResponseDto updateComment(CommentRequestDto requestDto,
        final Long commentId,
        final User user) {

        Comment comment = findCommentById(commentId);
        User Author = findUserById(user.getId());
        validateCommentUser(Author,comment);

        comment.update(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Override
    public void deleteComment(Long commentId, User user) {
        Comment comment = findCommentById(commentId);
        User Author = findUserById(user.getId());
        validateCommentUser(Author,comment);

        commentRepository.delete(comment);
    }


    private Post findPostById(Long id){
        return postRepository.findById(id)
            .orElseThrow(()->new NotFoundPostException(PostErrorCode.NOT_FOUND_POST));

    }

    private User findUserById(Long id){
        return userRepository.findById(id)
            .orElseThrow(()->new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
    }

    private Comment findCommentById(Long id){
        return commentRepository.findById(id)
            .orElseThrow(()->new NotFoundCommentException(CommentErrorCode.NOT_FOUND_Comment));
    }

    private void validateCommentUser(User user, Comment comment){
        if(!comment.getUser().equals(user)){
            throw new ForbiddenAccessCommentException(CommentErrorCode.FORBIDDEN_ACCESS_COMMENT);
        }
    }

}

