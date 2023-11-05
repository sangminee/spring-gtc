package com.example.gtc.domain.chat.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "채팅 방 정보")
public class PostChatRoomRes {
    private Long chatRoomId;
    private String toNickname;
    private String fromNickname;
    private int state;
}
