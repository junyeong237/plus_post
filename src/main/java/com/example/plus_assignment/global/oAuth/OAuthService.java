package com.example.plus_assignment.global.oAuth;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OAuthService {

    public String oAuthLogin(String code) throws JsonProcessingException;


    String getToken(String code) throws JsonProcessingException;



}
