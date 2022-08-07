package com.example.gtc.src.chat.controller;

import com.example.gtc.config.BaseException;
import com.example.gtc.config.BaseResponse;
import com.example.gtc.src.chat.repository.dto.response.*;
import com.example.gtc.src.chat.service.ChatServiceImpl;
import com.example.gtc.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.gtc.config.BaseResponseStatus.*;

@RestController
@Api(tags ="채팅 API")
public class ChatController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatServiceImpl chatService;
    private final JwtService jwtService;

    @Autowired
    public ChatController(ChatServiceImpl chatService, JwtService jwtService) {
        this.chatService = chatService;
        this.jwtService = jwtService;
    }


    /**
     * 채팅 방 생성 API
     * [POST]  http://localhost:8080/chat
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "채팅 방 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = PostChatRoomRes.class),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다.")
    })
    @PostMapping("/chat")
    public BaseResponse<?> createChatRoom(@RequestBody Map<String, String> map){
        if(map.get("from-UserId").equals(" ")){
            return new BaseResponse<>(EMPTY_FROM_USER);
        }
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostChatRoomRes postChatRoomRes = chatService.createChatRoom(userId, map.get("from-UserId"));
            return new BaseResponse<>(postChatRoomRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

//    /**
//     * 채팅하기 API
//     * [POST]  http://localhost:8080/chat/{chatRoomId}
//     * @return BaseResponse<?>
//     */
//    @ApiOperation(value = "채팅하기")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "OK",response = PostChatRoomRes.class),
//            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
//            @ApiResponse(code = 3021, message = "정지된 계정입니다.")
//    })
//    @PostMapping("/chat")
//    public BaseResponse<?> createChat(@RequestBody Map<String, String> map){
//        try{
//            // jwt
//            Long userId = jwtService.getUserIdx();
//            PostChatRoomRes postChatRoomRes = chatService.createChatRoom(userId, map.get("채팅 상대"));
//            return new BaseResponse<>(postChatRoomRes);
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

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
