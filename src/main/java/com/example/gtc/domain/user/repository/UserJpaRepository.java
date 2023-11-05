package com.example.gtc.domain.user.repository;

import com.example.gtc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, String>{

//    Page<User> findByUserOrderByIdDesc(User user, Pageable pageable);

    Optional<User> findByUserId(Long userId);
    Optional<User> findByPhone(String phone);
    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);
}
