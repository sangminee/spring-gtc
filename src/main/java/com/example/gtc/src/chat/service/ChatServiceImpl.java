package com.example.gtc.src.chat.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.chat.entity.Chat;
import com.example.gtc.src.chat.entity.ChatRoom;
import com.example.gtc.src.chat.repository.ChatJpaRepository;
import com.example.gtc.src.chat.repository.ChatRoomJpaRepository;
import com.example.gtc.src.chat.repository.dto.response.GetChatRoomRes;
import com.example.gtc.src.chat.repository.dto.response.PostChatRes;
import com.example.gtc.src.chat.repository.dto.response.PostChatRoomRes;
import com.example.gtc.src.comment.entity.CommentLike;
import com.example.gtc.src.comment.repository.dto.response.PostSuccessRes;
import com.example.gtc.src.post.repository.dto.response.GetPost;
import com.example.gtc.src.user.entity.User;
import com.example.gtc.src.user.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.gtc.config.BaseResponseStatus.*;

@Service
public class ChatServiceImpl implements ChatService{

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserJpaRepository userJpaRepository;
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ChatJpaRepository chatJpaRepository;

    public ChatServiceImpl(UserJpaRepository userJpaRepository, ChatRoomJpaRepository chatRoomJpaRepository, ChatJpaRepository chatJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.chatRoomJpaRepository = chatRoomJpaRepository;
        this.chatJpaRepository = chatJpaRepository;
    }


    @Override
    public PostChatRoomRes createChatRoom(Long userId, String fromUserId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<User> fromUser = userJpaRepository.findByNickname(fromUserId);
        List<ChatRoom> allChatRoom = chatRoomJpaRepository.findAll();

        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        if(fromUser.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }

        boolean flag = false;
        for(int i=0; i<allChatRoom.size(); i++){
            if(allChatRoom.get(i).getFromUserId().equals(fromUser.get())){
                flag = true;
            }
        }
        if(flag == true){ // 이미 채팅룸이 존재함
            throw new BaseException(DUPLICATED_FROM_USER);
        }else{
            ChatRoom chatRoom = ChatRoom.toEntity(user.get(), fromUser.get());
            chatRoomJpaRepository.save(chatRoom);
            return new PostChatRoomRes(chatRoom.getChatRoomId(), chatRoom.getUserId().getNickname(),
                    chatRoom.getFromUserId().getNickname(), chatRoom.getState());
        }
    }

    @Override
    public PostChatRes createChat(Long userId, Long chatRoomId, String chatContent) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        Optional<ChatRoom> chatRoom = chatRoomJpaRepository.findById(chatRoomId);
        if(chatRoom.isEmpty()){

        }
        try{
            Chat chat = Chat.toEntity(chatRoom.get(), user.get(), chatContent);
            chatJpaRepository.save(chat);
            return new PostChatRes(chatRoomId, user.get().getNickname(),
                    chatRoom.get().getFromUserId().getNickname(),chatContent, chat.getMessageCreateTime());
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public List<GetChatRoomRes> getChatRoom(Long userId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        try{
            List<ChatRoom> allChatRoom = chatRoomJpaRepository.findAll();
            List<GetChatRoomRes> myChatRoom = new ArrayList<>();
            for(int i=0; i< allChatRoom.size(); i++){
                if(allChatRoom.get(i).getUserId().getUserId() == userId){
                    GetChatRoomRes getChatRoomRes = new GetChatRoomRes();
                    getChatRoomRes.setChatRoomId(allChatRoom.get(i).getChatRoomId());
                    getChatRoomRes.setUserNickname(allChatRoom.get(i).getUserId().getNickname());
                    getChatRoomRes.setFromNickname(allChatRoom.get(i).getFromUserId().getNickname());

                    myChatRoom.add(getChatRoomRes);
                }
            }
            return myChatRoom;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
