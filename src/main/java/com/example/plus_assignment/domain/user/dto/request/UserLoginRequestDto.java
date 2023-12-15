package com.example.plus_assignment.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequestDto {

    private String nickname;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 영문자, 숫자를 포함한 8글자 이상 15글자 이하입니다.")
    private String password;
}
