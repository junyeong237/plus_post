package com.example.plus_assignment.global.mail;

import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
import jakarta.mail.internet.MimeMessage;

public interface MailService {

    String sendMail(String mail);

    boolean checkCode(UserCheckCodeRequestDto checkCodeRequestDto);

}
