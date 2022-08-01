package com.example.gtc.src.chat.repository;

import com.example.gtc.src.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom,Long> {
}
