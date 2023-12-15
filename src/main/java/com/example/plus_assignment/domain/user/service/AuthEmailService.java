package com.example.plus_assignment.domain.user.service;

public interface AuthEmailService {

    Boolean getAuthEmailIsChecked(String email);
    void createAuthEmail(String email,String code);
    void completedAuth(String email, String code);

}
