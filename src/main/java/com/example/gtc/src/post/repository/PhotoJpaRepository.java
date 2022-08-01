package com.example.gtc.src.post.repository;

import com.example.gtc.src.comment.entity.Comment;
import com.example.gtc.src.post.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJpaRepository extends JpaRepository<Photo,Long> {
}
