package com.example.gtc.src.chat.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.chat.repository.dto.response.GetChatRes;
import com.example.gtc.src.chat.repository.dto.response.GetChatRoomRes;
import com.example.gtc.src.chat.repository.dto.response.PostChatRes;
import com.example.gtc.src.chat.repository.dto.response.PostChatRoomRes;

import java.util.List;

public interface ChatService {

    PostChatRoomRes createChatRoom(Long userId, String fromUserId) throws BaseException;

    PostChatRes createChat(Long userId, Long chatRoomId, String chatContent) throws BaseException;
    List<GetChatRoomRes> getChatRoom(Long userId) throws BaseException;

    List<GetChatRes> getChats(Long userId, Long chatRoomId)  throws BaseException;
}
