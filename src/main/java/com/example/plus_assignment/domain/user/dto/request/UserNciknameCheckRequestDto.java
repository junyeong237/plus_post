package com.example.plus_assignment.domain.user.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNciknameCheckRequestDto {

    String nickname;

}
