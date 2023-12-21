package com.example.plus_assignment.global.mail.impl;

import com.example.plus_assignment.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.plus_assignment.domain.user.dto.request.UserSendMailRequestDto;
import com.example.plus_assignment.domain.user.entity.AuthEmail;
import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
import com.example.plus_assignment.global.mail.MailService;
import com.example.plus_assignment.global.redis.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private int authNumber;
    private final JavaMailSender emailSender;
    private final AuthEmailRepository authEmailRepository;
    private final RedisUtil redisUtil;
    private static final int DURATION = 3;

    @Value("${spring.mail.username}")
    private String email;

    //임의의 6자리 양수를 반환합니다.
    private void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
    }


    public String sendMail(String mail){
        makeRandomNumber();
        MimeMessage emailForm = createEmailForm(mail);

//        String code = createAuthCode();
//        MimeMessage message = createMessage(to, subject, code);
//
        if (redisUtil.hasMail(mail)) {
            redisUtil.deleteMail(mail);
        }
        redisUtil.addMailList(mail, String.valueOf(authNumber), DURATION);
//        mailSender.send(message);

        //try {
            emailSender.send(emailForm);
//        } catch (RuntimeException e) {
//
//            //throw new BusinessLogicException(ExceptionCode.UNABLE_TO_SEND_EMAIL);
//            throw new IllegalArgumentException("일단 오류처리");
//        }
        return String.valueOf(authNumber);
    }

    // 발신할 이메일 데이터 세팅
    private MimeMessage createEmailForm(String toEmail) {
        System.out.println("toEmail = " + toEmail);
        MimeMessage message = emailSender.createMimeMessage();
        //SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(email); //
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + authNumber + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }


    public boolean checkCode(UserCheckCodeRequestDto checkCodeRequestDto) {

        try {
            Object authCode = redisUtil.getCode(checkCodeRequestDto.getEmail());
            return authCode.equals(checkCodeRequestDto.getCode());
        } catch (Exception e) {
            throw new IllegalArgumentException("해당하는 이메일이없슴");
        }
//        AuthEmail email = authEmailRepository.findByEmail(checkCodeRequestDto.getEmail())
//            .orElseThrow(()->new IllegalArgumentException("해당하는 이메일이없슴"));

//        if(checkCodeRequestDto.getCode().equals(email.getCode())){
//            return true;
//        }

    }
}
