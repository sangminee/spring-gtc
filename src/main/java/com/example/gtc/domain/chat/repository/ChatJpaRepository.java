package com.example.gtc.domain.chat.repository;

import com.example.gtc.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatJpaRepository extends JpaRepository<Chat,Long> {
}
