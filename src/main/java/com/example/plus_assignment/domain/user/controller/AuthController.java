package com.example.plus_assignment.domain.user.controller;

import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserNciknameCheckRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSignUpRequestDto;
import com.example.plus_assignment.domain.user.service.impl.AuthServiceImpl;
import com.example.plus_assignment.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthController {

    private final AuthServiceImpl authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignUpRequestDto request) {

        authService.signup(request);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/signup/check")
    public ResponseEntity<?> passwordCheck(@RequestBody UserNciknameCheckRequestDto request) {

        authService.checkPassword(request);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDto request,
        HttpServletResponse res) {

        authService.login(request,res);

        return ResponseEntity.ok().build();
    }


}
