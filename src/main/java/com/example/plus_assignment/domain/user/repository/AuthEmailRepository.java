package com.example.plus_assignment.domain.user.repository;

import com.example.plus_assignment.domain.user.entity.AuthEmail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthEmailRepository extends JpaRepository<AuthEmail,Long> {

    Optional<AuthEmail> findByEmail(String email);

    Boolean existsByEmailAndIsChecked(String email,Boolean bool);



}
