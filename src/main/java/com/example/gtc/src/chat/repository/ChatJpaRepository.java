package com.example.gtc.src.chat.repository;

import com.example.gtc.src.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatJpaRepository extends JpaRepository<Chat,Long> {
}
