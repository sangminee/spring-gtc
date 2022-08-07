package com.example.gtc.src.comment.repository;

import com.example.gtc.src.comment.entity.Comment;
import com.example.gtc.src.comment.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReportJpaRepository extends JpaRepository<CommentReport,Long> {

}
