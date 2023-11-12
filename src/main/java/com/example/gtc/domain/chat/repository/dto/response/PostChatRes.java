package com.example.gtc.domain.chat.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "채팅 내용")
public class PostChatRes {
    private Long chatRoomId;
    private String toUserNickname;
    private String fromUserNickname;
    private String chatContent;
    private LocalDateTime messageCreateTime;
}
