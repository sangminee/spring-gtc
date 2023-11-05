package com.example.gtc.domain.post.repository;

import com.example.gtc.domain.post.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagJpaRepository extends JpaRepository<PostTag,Long> {
}
