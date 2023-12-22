package com.example.plus_assignment;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSendMailRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.dto.response.UserCheckCodeResponseDto;
import com.example.plus_assignment.domain.user.entity.AuthEmail;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import com.example.plus_assignment.domain.user.service.impl.AuthEmailServiceImpl;
import com.example.plus_assignment.domain.user.service.impl.AuthServiceImpl;
import com.example.plus_assignment.global.mail.impl.MailServiceImpl;
import com.example.plus_assignment.global.redis.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.

public class AuthIntegrationTest {



    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private RedisUtil redisUtil;



    @Autowired
    private AuthEmailRepository authEmailRepository;

    @Autowired
    private HttpServletResponse response;

    private static final int DURATION = 30;


    @Test
    @Order(1)
    @DisplayName("이메일인증후 코드확인")
    void 이메일인증_코드확인() {
        UserSendMailRequestDto requestDto = UserSendMailRequestDto.builder()
            .email("test@naver.com")
            .build();


        //authService.sendMail(requestDto); //이메일이 잘 발송됐다고 가정

        AuthEmail authEmail = AuthEmail.builder()
            .email(requestDto.getEmail())
            .isChecked(Boolean.FALSE)
            .build();

        authEmailRepository.save(authEmail);

        redisUtil.addMailList(requestDto.getEmail(), "이메일인증코드입니다.", DURATION);

        UserCheckCodeRequestDto codeRequestDto  = UserCheckCodeRequestDto.builder()
                .code("이메일인증코드입니다.")
                    .email("test@naver.com")
                        .build();

        UserCheckCodeResponseDto responseDto = authService.checkCode(codeRequestDto);

        assertEquals("인증이 완료되었습니다.", responseDto.getMessage());
        assertEquals(true, responseDto.getIsChecked());


    }
    @Test
    @Order(2)
    @DisplayName("이메일인증후 회원가입")
    void 회원가입() {


        UserSignUpRequestDto signUpRequestDto = UserSignUpRequestDto.builder()
            .nickname("parak")
            .password("123456789")
            .passwordCheck("123456789")
            .email("test@naver.com")
            .build();


        authService.signup(signUpRequestDto);


    }

    @Test
    @Order(3)
    @DisplayName("회원가입 후 로그인")
    void 로그인() {


        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
            .nickname("parak")
            .password("123456789")
            .build();

        authService.login(requestDto,response);


    }






}
