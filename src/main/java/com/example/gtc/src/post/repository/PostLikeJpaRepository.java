package com.example.gtc.src.post.repository;

import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeJpaRepository extends JpaRepository<PostLike,Long> {
}
