package com.example.gtc.domain.comment.repository;

import com.example.gtc.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment,Long> {
}
