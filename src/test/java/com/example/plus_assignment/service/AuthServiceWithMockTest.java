package com.example.plus_assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import com.example.plus_assignment.domain.user.service.impl.AuthEmailServiceImpl;
import com.example.plus_assignment.domain.user.service.impl.AuthServiceImpl;
import com.example.plus_assignment.global.jwt.JwtUtil;
import com.example.plus_assignment.global.redis.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) // 기본값 테스트마다 인스턴스 공유안함
@Transactional
@ExtendWith(MockitoExtension.class) // junit5용

public class AuthServiceWithMockTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepositry userRepository;

    @Mock
    private AuthEmailRepository authEmailRepository;
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisUtil redisUtil;

    @Mock
    private AuthEmailServiceImpl authEmailService;

    @Mock
    private HttpServletResponse response;


    @Test
    @DisplayName("회원가입 테스트")
    public void testCreate() {

        UserSignUpRequestDto signUpRequestDto = UserSignUpRequestDto.builder()
            .nickname("parak")
            .password("123456789")
            .passwordCheck("123456789")
            .email("12345@naver.com")
            .build();

        given(userRepository.existsByNickname(signUpRequestDto.getNickname()))
            .willReturn(false);
        given(userRepository.existsByEmail(signUpRequestDto.getEmail()))
            .willReturn(false);
        given(authEmailService.getAuthEmailIsChecked(signUpRequestDto.getEmail()))
            .willReturn(true);

        authService.signup(signUpRequestDto);
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));

    }


    @Test
    @DisplayName("로그인 테스트")
    public void 로그인테스트(){
        jwtUtil.init();
        String token = "Bearer StringToken";
        String refreshToken = "Bearer RefreshToken";
        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
            .nickname("parak")
            .password("123456789")
            .build();

        User user = User.builder()
                .nickname("parak")
                    .password("123456789")
                        .role(UserRoleEnum.USER)
                            .email("1234@naver.com")
                                .build();
//
        given(userRepository.findByNickname(requestDto.getNickname()))
            .willReturn(Optional.of(user));
//
        given(passwordEncoder.matches("123456789","123456789"))
            .willReturn(true);

        given(jwtUtil.createAccessToken(user.getNickname(), user.getRole().getAuthority()))
            .willReturn(token);

        given(jwtUtil.createRefreshToken())
            .willReturn(refreshToken);

        authService.login(requestDto,response);


    }



}
