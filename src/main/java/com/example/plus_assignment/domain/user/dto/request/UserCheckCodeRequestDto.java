package com.example.plus_assignment.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserCheckCodeRequestDto {
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "적절한 이메일 형식이 아닙니다.")
    private String email;

    private String code;

    @Builder
    private UserCheckCodeRequestDto(String email, String code){
        this.email = email;
        this.code = code;
    }
}
