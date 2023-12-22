package com.example.plus_assignment.controller;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.plus_assignment.domain.post.controller.PostController;
import com.example.plus_assignment.domain.post.service.Impl.PostServiceImpl;
import com.example.plus_assignment.domain.user.controller.AuthController;
import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.service.impl.AuthServiceImpl;
import com.example.plus_assignment.global.config.MockSpringSecurityFilter;
import com.example.plus_assignment.global.config.WebSecurityConfig;
import com.example.plus_assignment.global.jwt.JwtUtil;
import com.example.plus_assignment.global.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {AuthController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;

    //@Mock//@Mock으로 선언하면 오류남
    @MockBean
    AuthServiceImpl authService;

    //@Mock
    @MockBean
    JwtUtil jwtUtil; // 이거 테스트코드에서 안쓰더라도 Controller코드에서 주입되야하는코드는 Mock으로라도 무조건 선언해줘야한다.

    private static final String BASE_URL = "/api";



    @BeforeEach
    public void mockUserSetup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build(); //이거를 안하면 오류? //security가 테스트하는데 방해돼서 가짜 filter를만들어서 테스트해준다?

    }


    @Test
    @DisplayName("회원가입")
    void signupTest() throws Exception {

        //given

        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder()
            .nickname("park")
            .password("123456789")
            .email("asdada@gmail.com")
            .passwordCheck("123456789")
            .build();
        //when

        String body = mapper.writeValueAsString(
            requestDto
        );

        //then
        mvc.perform(post(BASE_URL + "/users/signup")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
            )
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("로그인")
    void loginTest() throws Exception {

        //given

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
            .nickname("park")
            .password("123456789")
            .build();
        //when

        String body = mapper.writeValueAsString(
            requestDto
        );

        //then
        mvc.perform(post(BASE_URL + "/users/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
            )
            .andExpect(status().isOk())
            .andDo(print());

    }


}
