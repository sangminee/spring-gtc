package com.example.gtc.src.chat.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags ="채팅 API")
public class ChatController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 채팅 방 생성 API
     * [POST]  http://localhost:8080/chat
     * @return BaseResponse<?>
     */

    /**
     * 채팅하기 API
     * [POST]  http://localhost:8080/chat/{chatRoomId}
     * @return BaseResponse<?>
     */

    /**
     * 전체 채팅 방 목록 API
     * [GET]  http://localhost:8080/chat
     * @return BaseResponse<?>
     */

    /**
     * 채팅 내용 보기 API
     * [GET]  http://localhost:8080/chat/{chatRoomId}
     * @return BaseResponse<?>
     */
    
}
