package com.example.gtc.src.chat.entity;

import com.example.gtc.src.user.entity.User;
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
    private User user;

    private int state;

}
