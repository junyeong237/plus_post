package com.example.plus_assignment.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.post.service.Impl.PostServiceImpl;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//
//@RunWith(MockitoJUnitRunner.class) // junit4용
@TestInstance(TestInstance.Lifecycle.PER_METHOD) // 기본값 테스트마다 인스턴스 공유안함

@ExtendWith(MockitoExtension.class) // junit5용
@Transactional
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepositry userRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    @DisplayName("Post 생성 테스트")
    public void testCreate() throws IOException {
        // Given
        Long userId = 1L;

        User user = User.builder()
                .id(userId)
                    .role(UserRoleEnum.USER)
                        .build();


        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목1")
            .content("내용1")
            .build();

        //When
        PostDetailResponseDto result = postService.createPost(postRequestDto, user);
        // Then
        assertEquals("내용1", result.getContent());
        Mockito.verify(postRepository, Mockito.times(1)).save(any(Post.class));


    }

    @Test
    @DisplayName("Post 수정 테스트")
    public void testUpdate() {
        // Given
        Long userId = 1L;

        User user = User.builder()
            .id(userId)
            .role(UserRoleEnum.USER)
            .build();


        //given(userRepository.findById(userId)).willReturn(Optional.of(user));
        //이 부분에서 오류가 난 이유는 이걸 설정해줬지만 실제 테스트에서 안쓰니까? 오류가 난듯하다?


        Long postId = 1L;

        Post post = Post.builder()
            .title("제목1")
            .content("내용1")
            .id(postId)
            .user(user)
            .build();

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목수정1")
            .content("내용수정1")
            .build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        //When
        PostDetailResponseDto result = postService.updatePost(user,postId,postRequestDto);
        // Then
        assertEquals("내용수정1", result.getContent());

    }


}
