package com.example.gtc.domain.chat.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "채팅방")
public class GetChatRoomRes {
    private Long chatRoomId;
    private String userNickname;
    private String fromNickname;
}
