package com.example.plus_assignment.domain.user.repository;

import com.example.plus_assignment.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositry extends JpaRepository<User,Long> {

    Boolean existsByNickname(String name);

    Boolean existsByEmail(String email);

    Optional<User> findByNickname(String name);


    //Optional<User> findByNicknameAndPassword(String name,String password);
}
