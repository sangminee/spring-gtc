package com.example.gtc.src.comment.repository;

import com.example.gtc.src.chat.entity.Chat;
import com.example.gtc.src.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment,Long> {
}
