package com.example.plus_assignment.domain.user.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpRequestDto {

    @Pattern(
        regexp = "^[a-zA-Z0-9]{3,}$",
        message = "로그인 닉네임은 a ~ z, A ~ Z, 0 ~ 9 만 포함, 3자 이상입니다.")
    private String nickname;

    @Pattern(
        regexp = "^[a-zA-Z0-9]{4,}$",
        message = "비밀번호는 a ~ z, A ~ Z, 0 ~ 9, 한글 만 포함, 4자이상") // a ~ z, A ~ Z, 0 ~ 9, 한글 만 포함, 2이상 20이하
    private String password;
    private String passwordCheck;

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "적절하지 않는 이메일 형식입니다.")
    private String email;


}
