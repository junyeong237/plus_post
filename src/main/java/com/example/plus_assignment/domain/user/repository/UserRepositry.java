package com.example.plus_assignment.domain.user.repository;

import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.global.oAuth.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositry extends JpaRepository<User,Long> {

    Boolean existsByNickname(String name);

    Boolean existsByEmail(String email);

    Optional<User> findByNickname(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType , Long socialId);

    Optional<User> findBySocialId(Long socialId);

    //Optional<User> findByNicknameAndPassword(String name,String password);
}
