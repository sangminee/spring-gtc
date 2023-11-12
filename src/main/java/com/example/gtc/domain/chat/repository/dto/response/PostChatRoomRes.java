package com.example.gtc.domain.chat.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "채팅 방 정보")
public class PostChatRoomRes {
    private Long chatRoomId;
    private String toNickname;
    private String fromNickname;
    private int state;
}
