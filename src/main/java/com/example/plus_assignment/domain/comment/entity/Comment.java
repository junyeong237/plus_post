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
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;


    private Boolean deleted= false;


    @OneToMany(mappedBy = "parent")
    private List<Comment> childList = new ArrayList<>();




    @Builder
    private Comment(Long id, String content, User user, Post post, Comment parent) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
        this.deleted = false;
        this.parent = parent;
    }

    public void update(String content){
        this.content = content;
    }


    //== 삭제 ==//
    public void remove() {
        this.deleted = true;
    }


    public List<Comment> findRemovableList() {

        List<Comment> result = new ArrayList<>();

        Optional.ofNullable(this.parent).ifPresentOrElse(

            parentComment ->{//대댓글인 경우 (부모가 존재하는 경우)
                if( parentComment.getDeleted()&& parentComment.isAllChildRemoved()){
                    result.addAll(parentComment.getChildList());
                    result.add(parentComment);
                }
            },

            () -> {//댓글인 경우
                if (isAllChildRemoved()) {
                    result.add(this);
                    result.addAll(this.getChildList());
                }
            }
        );

        return result;
    }

    //모든 자식 댓글이 삭제되었는지 판단
    private boolean isAllChildRemoved() {
        return this.childList.stream()
            .map(Comment::getDeleted)
            .filter(isDeleted -> !isDeleted)//지워졌으면 true, 안지워졌으면 false이다. 따라서 filter에 걸러지는 것은 false인 녀석들이고, 있다면 false를 없다면 orElse를 통해 true를 반환한다.
            .findAny()//지워지지 않은게 하나라도 있다면 false를 반환
            .orElse(true);//모두 지워졌다면 true를 반환
    }


}
