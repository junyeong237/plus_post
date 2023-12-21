package com.example.plus_assignment.domain.user.service.impl;

import com.example.plus_assignment.domain.user.entity.AuthEmail;
import com.example.plus_assignment.domain.user.repository.AuthEmailRepository;
import com.example.plus_assignment.domain.user.service.AuthEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthEmailServiceImpl implements AuthEmailService {

    private final AuthEmailRepository authEmailRepository;
    @Override
    @Transactional(readOnly = true)
    public Boolean getAuthEmailIsChecked(final String emailName) {
        AuthEmail authEmail = findEmailByName(emailName);

        return authEmail.getIsChecked();
    }

    @Override
    public void createAuthEmail(String emailName) {

        if(authEmailRepository.existsByEmailAndIsChecked(emailName,true)){
            return; // 이메일인증이 이미 끝난 이메일입니다. 메세지
        }
        AuthEmail authEmail = AuthEmail.builder()
            .email(emailName)
            .isChecked(Boolean.FALSE)
            .build();

        authEmailRepository.save(authEmail);

    }

    @Override
    public void completedAuth(final String emailName,final String code) {

        AuthEmail email = findEmailByName(emailName);

        email.updateChecked();
        //email.updateCode(code);

    }

    private AuthEmail findEmailByName(String email){

        return authEmailRepository.findByEmail(email)
            .orElseThrow(()-> new IllegalArgumentException("해당하는 이메일을 찾을 수 없습니다."));

    }
}
