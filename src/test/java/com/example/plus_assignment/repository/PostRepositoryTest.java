package com.example.plus_assignment.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

//@DataJpaTest
@SpringBootTest
@Transactional
public class PostRepositoryTest {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepositry userRepository;


    private User user;
    private User tempUser;
    private Post post;

    @BeforeEach
    void setup() {
        // Given
        userRepository.deleteAll();
        postRepository.deleteAll();
        this.user = User.builder()
            .nickname("park")
            .password("123456789")
            .role(UserRoleEnum.USER)
            .email("123@naver.com")
            .build();
        userRepository.save(user);

        this.tempUser = User.builder()
            .nickname("아무사람")
            .password("123456789")
            .email("temptemp@naver.com")
            .role(UserRoleEnum.USER)
            .build();
        userRepository.save(tempUser);

        this.post = Post.builder()
            .content("내용입니다.")
            .id(1L)//이거 안해줘도 오류안남
            .title("제목입니다.")
            .user(user)
            .build();
        postRepository.save(post);
    }

    @Test
    @DisplayName("Repository 테스트")
    //@Transactional
    void test() {
        //setup();
        // When
        Optional<Post> foundPost = postRepository.findById(1L);

        // Then
        assertTrue(foundPost.isPresent());
        assertEquals(post.getId(), foundPost.get().getId());

    }

    @Test
    @DisplayName("Post 전체 조회 테스트")
    void test2() {

        Post post1 = Post.builder()
            .content("내용입니다.")
            .title("제목입니다.")
            .user(user)
            .build();
        postRepository.save(post1);


        Post post2 = Post.builder()
            .content("내용2입니다.")
            .title("제목2입니다.")
            .user(user)
            .build();
        postRepository.save(post2);

        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "title");
        Pageable pageable = PageRequest.of(0, 3, sort);
        // 0이 첫번쨰 페이지다!!!!!!!!!


        // When

        Page<Post> postPageList = postRepository.findAll(pageable);
        List<Post> result =  postPageList.getContent();
        // Then

        assertEquals(3, result.size());
        assertTrue(result.contains(post1));
        assertTrue(result.contains(post2));


    }
    @Test
    @DisplayName("Post 전체 조회 테스트 with queryDsl")
    void test3() {

        Post post1 = Post.builder()
            .content("내용입니다.")
            .title("제목입니다.")
            .user(user)
            .build();
        postRepository.save(post1);


        Post post2 = Post.builder()
            .content("내용2입니다.")
            .title("제목2입니다.")
            .user(user)
            .build();
        postRepository.save(post2);

        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "title");
        Pageable pageable = PageRequest.of(0, 3, sort);
        String search = "";
        // 0이 첫번쨰 페이지다!!!!!!!!!


        // When

        Page<Post> postPageList = postRepository.searchPostListWithSearchAndPaging(search,pageable);
        List<Post> result =  postPageList.getContent();
        // Then

        assertEquals(3, result.size());
        assertTrue(result.contains(post1));
        assertTrue(result.contains(post2));


    }

}
