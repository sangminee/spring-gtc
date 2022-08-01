package com.example.gtc.src.post.repository;

import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.post.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagJpaRepository extends JpaRepository<PostTag,Long> {
}
