package com.example.plus_assignment;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostEachTransactionalTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepositry userRepository;


    private User user;

    @Autowired
    private PostServiceImpl postService;

    @BeforeEach
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
    @Transactional
    void 게시물생성() throws IOException {
        //setup();
        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목2")
            .content("내용2")
            .build();

        User finduser = userRepository.findByNickname(user.getNickname()).orElse(null);

        PostDetailResponseDto responseDto = postService.createPost(postRequestDto, finduser);
        assertNotNull(responseDto);

        assertEquals("제목2", responseDto.getTitle());
        assertEquals("내용2", responseDto.getContent());

    }


    @Test
    @Order(2)
    @DisplayName("게시물 수정 테스트")
    @Transactional
        //@Transactional(readOnly = true)
    void 게시물수정() throws IOException {

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목2")
            .content("내용2")
            .build();

        User finduser = userRepository.findByNickname(user.getNickname()).orElse(null);

        PostDetailResponseDto responseDto = postService.createPost(postRequestDto, finduser);

        PostRequestDto postUpdateRequestDto = PostRequestDto.builder()
            .title("제목수정2")
            .content("내용수정2")
            .build();

        PostDetailResponseDto responseUpdateDto = postService.updatePost(finduser,responseDto.getId(),postUpdateRequestDto);

        assertNotNull(responseUpdateDto);


        assertEquals("제목수정2", responseUpdateDto.getTitle());
        assertEquals("내용수정2", responseUpdateDto.getContent());

    }


    @Test
    @Order(3)
    @DisplayName("게시물 삭제&조회 테스트")
    @Transactional
    void 게시물삭제후조회() throws IOException {
        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목2")
            .content("내용2")
            .build();

        User finduser = userRepository.findByNickname(user.getNickname()).orElse(null);

        PostDetailResponseDto responseDto = postService.createPost(postRequestDto, finduser);
        assertNotNull(responseDto);

        assertEquals("제목2", responseDto.getTitle());
        assertEquals("내용2", responseDto.getContent());

        User findUser = userRepository.findByNickname(user.getNickname()).orElse(null);

        postService.deletePost(findUser,responseDto.getId());

        List<PostPreviewResponseDto> postlist =
            postService.getPostAll(0,3,"title",true).getContent();

        assertEquals(true, postlist.isEmpty());
        assertEquals(0, postlist.size());

        List<User> userList = userRepository.findAll();

        assertEquals(1,userList.size());

    }

}
