package com.example.gtc.domain.post.repository;

import com.example.gtc.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById(Long postId);
}
