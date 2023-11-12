package com.example.gtc.domain.post.infrastructure;

import com.example.gtc.domain.post.infrastructure.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeJpaRepository extends JpaRepository<PostLike,Long> {
}
