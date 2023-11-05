package com.example.gtc.domain.user.repository;

import com.example.gtc.domain.user.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingJpaRepository extends JpaRepository<Following,Long> {
}
