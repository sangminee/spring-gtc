package com.example.gtc.domain.chat.controller;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.config.BaseResponse;
import com.example.gtc.domain.chat.repository.dto.response.*;
import com.example.gtc.domain.chat.service.ChatServiceImpl;
import com.example.gtc.common.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.gtc.common.config.BaseResponseStatus.*;

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

    /**
     * 채팅하기 API
     * [POST]  http://localhost:8080/chat/{chatRoomId}
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "채팅하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = PostChatRes.class),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다."),
            @ApiResponse(code = 2040, message = "게시글 내용을 입력해 주세요."),
            @ApiResponse(code = 2041, message = "글자수를 확인해 주세요.")
    })
    @PostMapping("/chat/{chatRoomId}")
    public BaseResponse<?> createChat(@RequestBody Map<String, String> map, @PathVariable Long chatRoomId){
        if(map.get("chat-content").equals("")){
            return new BaseResponse<>(POST_POSTS_EMPTY_CONTENT);
        }
        if(map.get("chat-content").length() >= 1000){
            return new BaseResponse<>(POST_POSTS_OVER_CONTENT);
        }
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostChatRes postChatRes = chatService.createChat(userId, chatRoomId, map.get("chat-content"));
            return new BaseResponse<>(postChatRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 전체 채팅 방 목록 API
     * [GET]  http://localhost:8080/chat
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "채팅 방 목록 조회 ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = GetChatRoomRes.class),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다.")
    })
    @GetMapping("/chat")
    public BaseResponse<?> getChatRoom(){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            List<GetChatRoomRes> allChat = chatService.getChatRoom(userId);
            return new BaseResponse<>(allChat);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 채팅 보기 API
     * [GET]  http://localhost:8080/chat/{chatRoomId}
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "채팅 방 목록 조회 ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = GetChatRes.class),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다.")
    })
    @GetMapping("/chat/{chatRoomId}")
    public BaseResponse<?> getChats(@PathVariable Long chatRoomId){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            List<GetChatRes> allChat = chatService.getChats(userId, chatRoomId);
            return new BaseResponse<>(allChat);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    
}
