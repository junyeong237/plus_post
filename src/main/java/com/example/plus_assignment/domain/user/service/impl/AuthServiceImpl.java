package com.example.plus_assignment.domain.user.service.impl;

import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserNciknameCheckRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSendMailRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.dto.response.UserCheckCodeResponseDto;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.entity.UserRoleEnum;
import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import com.example.plus_assignment.domain.user.service.AuthEmailService;
import com.example.plus_assignment.domain.user.service.AuthService;
import com.example.plus_assignment.global.jwt.JwtUtil;
import com.example.plus_assignment.global.mail.impl.MailServiceImpl;
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
    @Override
    public void signup(final UserSignUpRequestDto signUpRequestDto) {
        validateDuplicateName(signUpRequestDto.getNickname());
        validatePassword(signUpRequestDto.getPassword(),signUpRequestDto.getPasswordCheck());

        if (userRepositry.existsByEmail(signUpRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (!authEmailService.getAuthEmailIsChecked(signUpRequestDto.getEmail())) {
            throw new IllegalArgumentException("인증되지않은 이메일입니다.");
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
        authEmailService.createAuthEmail(requestDto.getEmail(),code);
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
    public String login(UserLoginRequestDto requestDto) {

        User user = userRepositry.findByNickname(requestDto.getNickname())
            .orElseThrow(()->new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요"));

        validatePassword(requestDto.getPassword(), user.getPassword());
        return jwtUtil.createAccessToken(user.getNickname(), user.getRole().getAuthority());
    }

    @Override
    public void checkPassword(final UserNciknameCheckRequestDto request) {
        validateDuplicateName(request.getNickname());
    }


    private void validateDuplicateName(String nickname){
        if (userRepositry.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    private void validatePassword(String ps, String psCheck){
        if(!passwordEncoder.matches(ps,psCheck)){
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요");
        }
    }

}
