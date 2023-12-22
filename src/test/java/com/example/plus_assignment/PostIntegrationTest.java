package com.example.plus_assignment;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.dto.response.PostPreviewResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.post.service.Impl.PostServiceImpl;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
public class PostIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepositry userRepository;


    private User user;
    private User tempUser;
    private Post post;


    private Long createdPostId;

    @Autowired
    private PostServiceImpl postService;

    @BeforeAll
    void setup() {
        // Given
        this.user = User.builder()
            .nickname("park")
            .password("123456789")
            .role(UserRoleEnum.USER)
            .email("123@naver.com")
            .build();
        userRepository.save(user);
    }



    @Test
    @Order(1)
    @DisplayName("게시물 생성테스트")
    //@Transactional(readOnly = true)
    void 게시물생성() throws IOException {
        //setup();
        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목2")
            .content("내용2")
            .build();

        User finduser = userRepository.findByNickname(user.getNickname()).orElse(null);

        PostDetailResponseDto responseDto = postService.createPost(postRequestDto, finduser);
        createdPostId = responseDto.getId();
        assertNotNull(responseDto);

        assertEquals("제목2", responseDto.getTitle());
        assertEquals("내용2", responseDto.getContent());

    }

    @Test
    @Order(2)
    @DisplayName("게시물 수정 테스트")
    //@Transactional(readOnly = true)
    void 게시물수정() {
        //setup();
        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목수정2")
            .content("내용수정2")
            .build();

        User findUser = userRepository.findByNickname(user.getNickname()).orElse(null);

        PostDetailResponseDto responseDto = postService.updatePost(findUser,createdPostId,postRequestDto);

        assertNotNull(responseDto);


        assertEquals("제목수정2", responseDto.getTitle());
        assertEquals("내용수정2", responseDto.getContent());

    }

    @Test
    @Order(3)
    @DisplayName("게시물 삭제&조회 테스트")
    void 게시물삭제후조회() {


        User findUser = userRepository.findByNickname(user.getNickname()).orElse(null);

        postService.deletePost(findUser,createdPostId);

        List<PostPreviewResponseDto> postlist =
            postService.getPostAll(0,3,"title",true).getContent();

        assertEquals(true, postlist.isEmpty());
        assertEquals(0, postlist.size());

    }


}
