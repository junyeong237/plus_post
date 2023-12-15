package com.example.plus_assignment.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_AUTH_EMAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Boolean isChecked;

    private String code = null;

    @Builder
    private AuthEmail(String email, Boolean isChecked,String code) {
        this.email = email;
        this.isChecked = isChecked;
        this.code = code;
    }

    public void updateChecked() {
        this.isChecked = true;
    }

    public void updateCode(String code){this.code = code;}
}
