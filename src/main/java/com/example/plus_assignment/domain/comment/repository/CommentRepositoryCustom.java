package com.example.plus_assignment.domain.comment.repository;

import com.example.plus_assignment.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryCustom {

    Page<Comment> getCommentList(Pageable pageable);

}
