package com.example.gtc.src.chat.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "채팅 내용")
public class PostChatRes {
    private Long chatRoomId;
    private String toUserNickname;
    private String fromUserNickname;
    private String chatContent;
    private LocalDateTime messageCreateTime;
}
