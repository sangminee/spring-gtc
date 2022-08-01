package com.example.gtc.src.comment.repository;

import com.example.gtc.src.comment.entity.Comment;
import com.example.gtc.src.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLike,Long> {
}
