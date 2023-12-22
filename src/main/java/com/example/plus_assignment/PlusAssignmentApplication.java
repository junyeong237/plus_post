package com.example.plus_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스프링 부트에서 스케줄러가 작동하게 합니다.
public class PlusAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlusAssignmentApplication.class, args);
    }

}
