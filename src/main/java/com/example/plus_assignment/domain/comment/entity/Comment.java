package com.example.plus_assignment.domain.comment.entity;

import com.example.plus_assignment.domain.model.TimeEntity;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    private Comment(Long id, String content, User user, Post post) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void update(String content){
        this.content = content;
    }

}
