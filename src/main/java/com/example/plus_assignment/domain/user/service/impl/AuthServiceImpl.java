package com.example.plus_assignment.domain.user.service.impl;

import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserNciknameCheckRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSendMailRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.dto.response.UserCheckCodeResponseDto;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.exception.ExistsUserEmailException;
import com.example.plus_assignment.domain.user.exception.ExistsUsernameException;
import com.example.plus_assignment.domain.user.exception.NotCorrectLoginInput;
import com.example.plus_assignment.domain.user.exception.PasswordMismatchException;
import com.example.plus_assignment.domain.user.exception.UnauthorizedEmailException;
import com.example.plus_assignment.domain.user.exception.UserErrorCode;
import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import com.example.plus_assignment.domain.user.service.AuthEmailService;
import com.example.plus_assignment.domain.user.service.AuthService;
import com.example.plus_assignment.global.jwt.JwtUtil;
import com.example.plus_assignment.global.mail.impl.MailServiceImpl;
import com.example.plus_assignment.global.redis.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepositry userRepositry;
    private final PasswordEncoder passwordEncoder;
    private final AuthEmailService authEmailService;
    private final MailServiceImpl mailService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    public final Integer REFRESH_TOKEN_TIME =  60 * 60 * 1000 * 24 * 14;
    @Override
    public void signup(final UserSignUpRequestDto signUpRequestDto) {
        validateDuplicateName(signUpRequestDto.getNickname());
        duplicatePassword(signUpRequestDto.getPassword(),signUpRequestDto.getPasswordCheck());

        if (userRepositry.existsByEmail(signUpRequestDto.getEmail())) {
            throw new ExistsUserEmailException(UserErrorCode.EXISTS_EMAIL);
        }
        if (!authEmailService.getAuthEmailIsChecked(signUpRequestDto.getEmail())) {
            throw new UnauthorizedEmailException(UserErrorCode.UNAUTHORIZED_EMAIL);
        }

        String encryptionPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        User user = User.builder()
            .nickname(signUpRequestDto.getNickname())
            .email(signUpRequestDto.getEmail())
            .password(encryptionPassword)
            .role(UserRoleEnum.USER)
            .build();

        userRepositry.save(user);
    }

    @Override
    public void sendMail(UserSendMailRequestDto requestDto) {
        String code = mailService.sendMail(requestDto.getEmail());
        authEmailService.createAuthEmail(requestDto.getEmail());
    }

    @Override
    public UserCheckCodeResponseDto checkCode(UserCheckCodeRequestDto requestDto) {
        boolean isChecked = mailService.checkCode(requestDto);
        //String message = FAIL_CHECK_CODE_MESSAGE;
        String message = "";
        if (isChecked) {
            message = "인증이 완료되었습니다.";
            authEmailService.completedAuth(requestDto.getEmail(),requestDto.getCode());
        }
        return UserCheckCodeResponseDto.builder()
            .isChecked(isChecked)
            .message(message)
            .build();
    }

    @Override
    public void login(UserLoginRequestDto requestDto, HttpServletResponse res) {

        User user = userRepositry.findByNickname(requestDto.getNickname())
            .orElseThrow( ()-> new NotCorrectLoginInput(UserErrorCode.NOT_CORRECT_LOGIN_INPUT));

        validatePassword(requestDto.getPassword(), user.getPassword());
        String accessToken = jwtUtil.createAccessToken(user.getNickname(), user.getRole().getAuthority());
        String refreshToken = jwtUtil.createRefreshToken();

        jwtUtil.addAccessJwtToCookie(accessToken, res);
        jwtUtil.addRefreshJwtToCookie(refreshToken, res);
        //response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        refreshToken = refreshToken.split(" ")[1].trim();

        redisUtil.set(refreshToken, user.getId(), REFRESH_TOKEN_TIME);

    }

    @Override
    public void checkPassword(final UserNciknameCheckRequestDto request) {
        validateDuplicateName(request.getNickname());
    }


    private void validateDuplicateName(String nickname){
        if (userRepositry.existsByNickname(nickname)) {
            throw new ExistsUsernameException(UserErrorCode.EXISTS_USERNAME);
        }
    }

    private void duplicatePassword(String ps, String psCheck){
        if(!ps.equals(psCheck)){
            throw new PasswordMismatchException(UserErrorCode.PASSWORD_MISMATCH);
        }
    }

    private void validatePassword(String ps, String psCheck){
        if(!passwordEncoder.matches(ps,psCheck)){
            throw new NotCorrectLoginInput(UserErrorCode.NOT_CORRECT_LOGIN_INPUT);
        }
    }

}
