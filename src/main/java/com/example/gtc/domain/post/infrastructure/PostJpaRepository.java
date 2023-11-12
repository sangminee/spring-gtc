package com.example.gtc.domain.post.infrastructure;

import com.example.gtc.domain.post.infrastructure.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById(Long postId);
}
