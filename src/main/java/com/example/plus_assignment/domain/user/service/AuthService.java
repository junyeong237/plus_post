package com.example.plus_assignment.domain.user.service;

import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserNciknameCheckRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSendMailRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.dto.response.UserCheckCodeResponseDto;

public interface AuthService {

    void signup(UserSignUpRequestDto signUpRequestDto);

    void checkPassword(UserNciknameCheckRequestDto request);
    void sendMail(UserSendMailRequestDto requestDto);
    UserCheckCodeResponseDto checkCode(UserCheckCodeRequestDto requestDto);

    String login(UserLoginRequestDto request);
}
