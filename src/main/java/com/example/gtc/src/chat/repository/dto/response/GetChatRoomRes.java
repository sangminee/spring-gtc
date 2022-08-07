package com.example.gtc.src.chat.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "채팅방")
public class GetChatRoomRes {
    private Long chatRoomId;
    private String userNickname;
    private String fromNickname;
}
