package com.example.gtc.domain.chat.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "채팅")
public class GetChatRes {
    private Long chatRoomId;
    private String writeNickname;
    private String chatContent;
    private LocalDateTime messageCreateTime;
}
