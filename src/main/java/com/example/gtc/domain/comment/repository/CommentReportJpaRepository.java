package com.example.gtc.domain.comment.repository;

import com.example.gtc.domain.comment.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportJpaRepository extends JpaRepository<CommentReport,Long> {

}
