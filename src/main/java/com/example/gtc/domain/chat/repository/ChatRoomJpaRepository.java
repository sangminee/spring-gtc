package com.example.gtc.domain.chat.repository;

import com.example.gtc.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom,Long> {
}
