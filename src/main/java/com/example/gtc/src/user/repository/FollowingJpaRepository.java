package com.example.gtc.src.user.repository;

import com.example.gtc.src.user.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingJpaRepository extends JpaRepository<Following,Long> {
}
