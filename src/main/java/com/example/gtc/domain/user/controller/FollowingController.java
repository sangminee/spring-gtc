package com.example.gtc.domain.user.controller;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.config.BaseResponse;
import com.example.gtc.domain.user.repository.dto.response.*;
import com.example.gtc.domain.user.service.UserServiceImpl;
import com.example.gtc.common.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name ="친구 신청 API")
@RestController
@RequiredArgsConstructor
public class FollowingController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserServiceImpl userService;
    private final JwtService jwtService;

    /**
     * 팔로우 신청 API
     * [POST]  http://localhost:8080/follower
     * @return BaseResponse<?>
     */
    @Operation(summary = "팔로우 신청")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "사용자 이름을 입력해주세요."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "3015", description = "없는 아이디거나 비밀번호가 틀렸습니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
    })
    @ResponseBody
    @PostMapping("/follower")
    public BaseResponse<List<PostFollowerRes>> createFollower(@RequestBody Map<String, String> map){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            List<PostFollowerRes> getList = userService.createFollower(userId, map.get("followingNickname"));
            return new BaseResponse<>(getList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 팔로워 요청 승인 API
     * [POST]  http://localhost:8080/following
     * @return BaseResponse<?>
     */

    /**
     * 팔로워 요청 승인 API
     * [POST]  http://localhost:8080/following
     * @return BaseResponse<?>
     */

}
