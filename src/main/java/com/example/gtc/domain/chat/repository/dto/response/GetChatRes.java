package com.example.gtc.domain.chat.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "채팅")
public class GetChatRes {
    private Long chatRoomId;
    private String writeNickname;
    private String chatContent;
    private LocalDateTime messageCreateTime;
}
