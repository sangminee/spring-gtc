package com.example.gtc.domain.comment.repository;

import com.example.gtc.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLike,Long> {

}
