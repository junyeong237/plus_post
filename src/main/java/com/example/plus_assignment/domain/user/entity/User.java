package com.example.plus_assignment.domain.user.entity;


import com.example.plus_assignment.domain.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users") // MySQL 에서 USER 는 예약어이므로 s를 붙임
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 에서는 기본 생성자가 필요하므로 최소 접근제어자로 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true) // 2이상 20이하
    private String nickname;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();
    //mappedBy 안쓰면 기본키 id값없는 중간테이블이 자동으로 생기더라..


    @Builder
    private User(Long id,String nickname, String password,String email,UserRoleEnum role) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

}
