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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

//@TestInstance를 안쓰고 @SpringBootTest만 써서 통합테스트 해보기
public class PostIntegrationNotTestInstanceTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepositry userRepository;


    private static User user; //테스트 메서드마다 인스턴스가 달라지기때문에 static이 아닌 그냥 user를 사용하면 안된다.

    @Autowired
    private PostServiceImpl postService;

    //@BeforeAll
    void setup() { //@TestInstance(TestInstance.Lifecycle.PER_CLASS) 가 없으면 무조건 beforeAll에서 static이 붙어ㅓ야함.
        //정적 메서드로 선언하는 이유는 테스트 클래스의 인스턴스를 생성하지 않고도 호출할 수 있도록 하기 위함입니다.
        // JUnit은 테스트 클래스의 인스턴스를 생성하지 않고도 @BeforeAll이 붙은 메서드를 호출하는데,
        // 이는 정적(static) 메서드만 호출할 수 있기 때문입니다.

        //@Before + static메서드로 사용할랬더니 userRepository도 static으로 선언해야하는데 이때 userRepository는
        //주입받아서 사용되는거라 뭔가 충돌이 나는듯하다.

        // Given
        user = User.builder() //static user가 아닐경우 각 테스트 인스턴스마다 this.user객체가 달라져서 static으로 공통적으로 사용
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
        setup();
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

        User findUser = userRepository.findByNickname(user.getNickname()).orElse(null);

        PostDetailResponseDto responseUpdateDto = postService.updatePost(findUser,responseDto.getId(),postUpdateRequestDto);

        assertNotNull(responseUpdateDto);


        assertEquals("제목수정2", responseUpdateDto.getTitle());
        assertEquals("내용수정2", responseUpdateDto.getContent());

    }

    @Test
    @Order(3)
    @DisplayName("게시물 삭제&조회 테스트")
    void 게시물삭제후조회() throws IOException {

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("제목2")
            .content("내용2")
            .build();

        User finduser = userRepository.findByNickname(user.getNickname()).orElse(null);

        PostDetailResponseDto responseDto = postService.createPost(postRequestDto, finduser);


        User findUser = userRepository.findByNickname(user.getNickname()).orElse(null);

        postService.deletePost(findUser,responseDto.getId());

        List<PostPreviewResponseDto> postlist =
            postService.getPostAll(0,3,"title",true).getContent();

        assertEquals(false, postlist.isEmpty());
        assertEquals(2, postlist.size());
        deleteAll();
    }


    void deleteAll(){
        userRepository.deleteAll();
        //이것도 트랜잭션이 안걸려있어서 수동으로 지워줘야하는듯?
    }

}
