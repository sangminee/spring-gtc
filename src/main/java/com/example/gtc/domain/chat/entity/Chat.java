package com.example.gtc.domain.chat.entity;

import com.example.gtc.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Chat{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long chatId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="chatRoomId")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = LAZY)
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
