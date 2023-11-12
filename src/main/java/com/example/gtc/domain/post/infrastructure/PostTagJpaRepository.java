package com.example.gtc.domain.post.infrastructure;

import com.example.gtc.domain.post.infrastructure.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagJpaRepository extends JpaRepository<PostTag,Long> {
}
