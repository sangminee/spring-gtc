package com.example.gtc.domain.post.infrastructure;

import com.example.gtc.domain.post.infrastructure.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJpaRepository extends JpaRepository<PhotoEntity,Long> {
}
