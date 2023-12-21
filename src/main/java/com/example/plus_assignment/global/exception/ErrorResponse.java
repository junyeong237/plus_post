package com.example.plus_assignment.global.exception;


import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    private String code;

    private List<String> messages = new ArrayList<>();
    @Builder
    public ErrorResponse(int status, String code, List<String> messages) {
        this.status = status;
        this.code = code;
        //this.messages = messages;
        this.messages = (messages != null) ? messages : new ArrayList<>();
    }

    public void addMessage(String message){
        //this.messages = new ArrayList<String>();
        //Builder에서 값을 안넣어주면 아예 null인건가?
        //
        //@Builder 어노테이션을 사용하여 빌더 패턴을 생성할 때,
        // 명시적으로 messages 필드를 초기화하지 않으면 해당 필드는 null 값이 됩니다
        this.messages.add(message);
        System.out.println("this.messages = " + this.messages);
    }
}


