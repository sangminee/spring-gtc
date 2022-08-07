package com.example.gtc.src.chat.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.chat.repository.dto.response.PostChatRoomRes;

public interface ChatService {

    PostChatRoomRes createChatRoom(Long userId, String fromUserId) throws BaseException;
}
