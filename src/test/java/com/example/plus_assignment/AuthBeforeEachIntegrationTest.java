//package com.example.plus_assignment;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
//import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
//import com.example.plus_assignment.domain.user.dto.request.UserSendMailRequestDto;
//import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
//import com.example.plus_assignment.domain.user.dto.response.UserCheckCodeResponseDto;
//import com.example.plus_assignment.domain.user.entity.AuthEmail;
//import com.example.plus_assignment.domain.user.entity.User;
//import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
//import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
//import com.example.plus_assignment.domain.user.repository.UserRepositry;
//import com.example.plus_assignment.domain.user.service.impl.AuthServiceImpl;
//import com.example.plus_assignment.global.redis.RedisUtil;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Transactional
//public class AuthBeforeEachIntegrationTest {
//    @Autowired
//    private AuthServiceImpl authService;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Autowired
//    UserRepositry userRepository;
//
//    @Autowired
//    private AuthEmailRepository authEmailRepository;
//
//    @Autowired
//    private HttpServletResponse response;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    private static final int DURATION = 30;
//
//    private User user;
//
//    @BeforeEach
//    void setup() {
//
//        AuthEmail authEmail = AuthEmail.builder()
//            .email("test2@naver.com")
//            .isChecked(Boolean.FALSE)
//            .build();
//
//        authEmailRepository.save(authEmail);
//        redisUtil.addMailList("test2@naver.com", "이메일인증코드입니다.", DURATION);
//
//        authEmail.updateChecked();
//        // Given
//        this.user = User.builder()
//            .nickname("kim")
//            .password(passwordEncoder.encode("123456789"))
//            .role(UserRoleEnum.USER)
//            .email("kim@naver.com")
//            .build();
//        userRepository.save(user);
//    }
//
//
//    @Test
//    @Order(1)
//    @DisplayName("이메일인증후 코드확인")
//    void 이메일인증_코드확인() {
//        UserSendMailRequestDto requestDto = UserSendMailRequestDto.builder()
//            .email("test@naver.com")
//            .build();
//
//
//        //authService.sendMail(requestDto); //이메일이 잘 발송됐다고 가정
//
//        AuthEmail authEmail = AuthEmail.builder()
//            .email(requestDto.getEmail())
//            .isChecked(Boolean.FALSE)
//            .build();
//
//        authEmailRepository.save(authEmail);
//
//        redisUtil.addMailList(requestDto.getEmail(), "이메일인증코드입니다.", DURATION);
//
//        UserCheckCodeRequestDto codeRequestDto  = UserCheckCodeRequestDto.builder()
//            .code("이메일인증코드입니다.")
//            .email("test@naver.com")
//            .build();
//
//        UserCheckCodeResponseDto responseDto = authService.checkCode(codeRequestDto);
//
//        assertEquals("인증이 완료되었습니다.", responseDto.getMessage());
//        assertEquals(true, responseDto.getIsChecked());
//
//
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("이메일인증후 회원가입")
//    void 회원가입() {
//
//
//        UserSignUpRequestDto signUpRequestDto = UserSignUpRequestDto.builder()
//            .nickname("parak")
//            .password("123456789")
//            .passwordCheck("123456789")
//            .email("test2@naver.com")
//            .build();
//
//
//        authService.signup(signUpRequestDto);
//
//
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("회원가입 후 로그인")
//    void 로그인() {
//
//
//        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
//            .nickname("kim")
//            .password("123456789")
//            .build();
//
//        authService.login(requestDto,response);
//
//
//    }
//
//}
