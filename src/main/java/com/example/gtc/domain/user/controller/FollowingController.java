package com.example.gtc.domain.user.controller;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.config.BaseResponse;
import com.example.gtc.domain.user.repository.dto.response.*;
import com.example.gtc.domain.user.service.UserServiceImpl;
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

@RestController
@Api(tags ="친구 신청 API")
public class FollowingController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserServiceImpl userService;
    private final JwtService jwtService;

    @Autowired  // 의존성 주입을 의한 것
    public FollowingController(UserServiceImpl userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 팔로우 신청 API
     * [POST]  http://localhost:8080/follower
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "팔로우 신청")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 1000, message = "OK"),
            @ApiResponse(code = 2013, message = "사용자 이름을 입력해주세요."),
            @ApiResponse(code = 2014, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 3015, message = "없는 아이디거나 비밀번호가 틀렸습니다."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다.")
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
