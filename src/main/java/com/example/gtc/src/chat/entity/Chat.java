package com.example.gtc.src.chat.entity;

import com.example.gtc.src.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chat{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long chatId;

    @ManyToOne
    @JoinColumn(name="chatRoomId")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name="writeUserId")
    private User user;

    private String chatContent;
    private LocalDateTime messageCreateTime;
    private int state;

    public static Chat toEntity(ChatRoom chatRoom, User user, String chatContent) {
        Chat chat = new Chat();
        chat.setChatRoom(chatRoom);
        chat.setUser(user);
        chat.setChatContent(chatContent);
        chat.setMessageCreateTime(LocalDateTime.now());
        chat.setState(0);
        return chat;
    }
}
