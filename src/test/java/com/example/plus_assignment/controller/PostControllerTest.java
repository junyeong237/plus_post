package com.example.plus_assignment.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.plus_assignment.domain.post.controller.PostController;
import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.service.Impl.PostServiceImpl;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.global.config.MockSpringSecurityFilter;
import com.example.plus_assignment.global.config.WebSecurityConfig;
import com.example.plus_assignment.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(
    controllers = {PostController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class // 이거 생략해도 되긴하더라?
        )
    }
)
//@SpringBootTest
//@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;

    @MockBean
    PostServiceImpl postService;

    private Principal mockPrincipal;
    private User testUser;

    private UserDetailsImpl testUserDetails;

    private static final String BASE_URL = "/api";


    @BeforeEach
    public void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "hwang";
        String password = "123456789";
        String email = "123asdad@naver.com";
        testUser = User.builder()
            .nickname(username)
            .password(password)
            .email(email)
            .role(UserRoleEnum.USER)
            .build();
        testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

//
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build(); //이거를 안하면 오류? //security가 테스트하는데 방해돼서 가짜 filter를만들어서 테스트해준다?

    }



    @Test
    @DisplayName("게시글 작성 테스트")
    void createPostTest() throws Exception {
        //given
        String title = "Test title";
        String content = "Test content";

        PostRequestDto requestDto = PostRequestDto.builder().content(content).title(title).build();

        //when
        /**
         * Object를 JSON으로 변환
         * */
        String body = mapper.writeValueAsString(
            requestDto
        );

        //then
        mvc.perform(post(BASE_URL + "/post")
                .content(body) //HTTP Body에 데이터를 담는다
                .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시

                .principal(mockPrincipal)
            )
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updatePostTest() throws Exception {
        // given
        Long postId = 1L;
        String title = "수정 제목입니다.";
        String content = "수정 내용입니다.";
        PostRequestDto requestDto = PostRequestDto.builder().content(content).title(title).build();

        // when - then
        String body = mapper.writeValueAsString(
            requestDto
        );

        mvc.perform(put(BASE_URL + "/post/{postId}", postId)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON) //필수
                .accept(MediaType.APPLICATION_JSON) //없어도 오류 안남
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk());
//            .andExpect(jsonPath("$.title").value(title))
//            .andExpect(jsonPath("$.content").value(content));

    }

    @Test
    @DisplayName("게시글 전체조회&검색,정렬 테스트")
    void getPostListTest() throws Exception {

        mvc.perform(get(BASE_URL + "/post/query")
            .param("search", "123")
            .param("sortBy", "title")
            .param("page", String.valueOf(1)) //여기서 보내줄때는 String가 보다
            .param("size", String.valueOf(3))
            .param("isAsc", String.valueOf(true)))
            .andExpect(status().isOk());

    }




}
