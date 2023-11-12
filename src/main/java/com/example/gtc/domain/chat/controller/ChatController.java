package com.example.gtc.domain.chat.controller;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.config.BaseResponse;
import com.example.gtc.domain.chat.repository.dto.response.*;
import com.example.gtc.domain.chat.service.ChatServiceImpl;
import com.example.gtc.common.utils.JwtService;
import com.example.gtc.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.gtc.common.config.BaseResponseStatus.*;

@Tag(name ="채팅 API")
@RestController
@RequiredArgsConstructor
public class ChatController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatServiceImpl chatService;
    private final JwtService jwtService;

    /**
     * 채팅 방 생성 API
     * [POST]  http://localhost:8080/chat
     * @return BaseResponse<?>
     */
    @Operation(summary = "채팅 방 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = PostChatRoomRes.class))),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
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
    @Operation(summary = "채팅하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = PostChatRes.class))),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다."),
            @ApiResponse(responseCode = "2040", description = "게시글 내용을 입력해 주세요."),
            @ApiResponse(responseCode = "2041", description = "글자수를 확인해 주세요.")
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
    @Operation(summary = "채팅 방 목록 조회 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = GetChatRoomRes.class))),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
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
    @Operation(summary = "채팅 방 목록 조회 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = GetChatRes.class))),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
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
