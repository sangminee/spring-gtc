package com.example.gtc.domain.post.repository;

import com.example.gtc.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeJpaRepository extends JpaRepository<PostLike,Long> {
}
