package com.example.plus_assignment.domain.user.controller;


import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSendMailRequestDto;
import com.example.plus_assignment.domain.user.service.impl.AuthServiceImpl;
import com.example.plus_assignment.global.mail.MailService;
import com.example.plus_assignment.global.mail.impl.MailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final MailServiceImpl mailService;
    private final AuthServiceImpl authService;

    @PostMapping("/send")
    public void mailSend(@RequestBody UserSendMailRequestDto mailRequestDto) {

        authService.sendMail(mailRequestDto);

    }

    @PostMapping("/checkcode")
    public void mailCheckCode(@RequestBody UserCheckCodeRequestDto checkCodeRequestDto) {

        authService.checkCode(checkCodeRequestDto);

    }
}
