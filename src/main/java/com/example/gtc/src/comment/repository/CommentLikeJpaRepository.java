package com.example.gtc.src.comment.repository;

import com.example.gtc.src.comment.entity.Comment;
import com.example.gtc.src.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLike,Long> {

}
