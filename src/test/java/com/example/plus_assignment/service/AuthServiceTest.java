package com.example.plus_assignment.service;


import static org.mockito.ArgumentMatchers.any;

import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.entity.AuthEmail;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import com.example.plus_assignment.domain.user.service.impl.AuthEmailServiceImpl;
import com.example.plus_assignment.domain.user.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) // 기본값 테스트마다 인스턴스 공유안함
@SpringBootTest
@Transactional
public class AuthServiceTest {


    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    private AuthEmailRepository authEmailRepository;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    public void 회원가입테스트() {

        UserSignUpRequestDto signUpRequestDto = UserSignUpRequestDto.builder()
            .nickname("parak1")
            .password("123456789")
            .passwordCheck("123456789")
            .email("12345@naver.com")
            .build();

        AuthEmail authEmail = AuthEmail.builder()
            .email("12345@naver.com")
            .isChecked(true)
            .build();

        authEmailRepository.save(authEmail);

        authService.signup(signUpRequestDto);

    }

    @Test
    @DisplayName("로그인 테스트")
    public void 로그인테스트() {

        User user = User.builder()
            .nickname("parakk")
            .password(passwordEncoder.encode("123456789"))
            .role(UserRoleEnum.USER)
            .email("1234@naver.com")
            .build();

        userRepositry.save(user);

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
            .nickname("parakk")
            .password("123456789")
            .build();

        authService.login(requestDto,response);



    }

}
