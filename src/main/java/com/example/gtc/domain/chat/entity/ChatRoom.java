package com.example.gtc.domain.chat.entity;

import com.example.gtc.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long chatRoomId;

    @ManyToOne
    @JoinColumn(name="userId")
    private User userId;

    @ManyToOne
    @JoinColumn(name="fromUserId")
    private User fromUserId;

    private int state;

    public static ChatRoom toEntity(User user, User fromUser) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUserId(user);
        chatRoom.setFromUserId(fromUser);
        chatRoom.setState(0);
        return chatRoom;
    }
}
