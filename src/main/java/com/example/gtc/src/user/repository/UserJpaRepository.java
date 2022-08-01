package com.example.gtc.src.user.repository;

import com.example.gtc.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, String>{

    Optional<User> findByUserId(Long userId);
    Optional<User> findByPhone(String phone);
    Optional<User> findByNickname(String nickname);

}
