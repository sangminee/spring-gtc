package com.example.gtc.domain.post.repository;

import com.example.gtc.domain.post.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJpaRepository extends JpaRepository<Photo,Long> {
}
