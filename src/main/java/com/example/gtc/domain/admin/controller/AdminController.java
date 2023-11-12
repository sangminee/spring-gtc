package com.example.gtc.domain.admin.controller;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.config.BaseResponse;
import com.example.gtc.domain.admin.service.AdminServiceImpl;
import com.example.gtc.domain.post.domain.dto.response.GetPost;
import com.example.gtc.domain.user.entity.User;
import com.example.gtc.common.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@Tag(name ="관리자 API")
@RestController
@RequiredArgsConstructor
public class AdminController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdminServiceImpl adminService;
    private final JwtService jwtService;

    /**
     * 회원 정보 전체 조회  API
     * [GET]  http://localhost:8080/admin/user
     * @return BaseResponse<?>
     */
    @Operation(summary = "회원 정보 전체 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
    })
    @GetMapping("/admin/user")
    public BaseResponse<?> getUsersbyAdmin(Pageable pageable){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            List<User> allUser = adminService.getUsersbyAdmin(userId);
            return new BaseResponse<>(allUser);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 정보 상세 조회  API
     * [GET]  http://localhost:8080/admin/user/{nickname}
     * @return BaseResponse<?>
     */

    /**
     * 전체 게시물 조회 API
     * [GET]  http://localhost:8080/admin/post
     * @return BaseResponse<?>
     */
    @Operation(summary = "전체 게시물 조회 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = GetPost.class))),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
    })
    @GetMapping("/admin/post")
    public BaseResponse<?> getPostsbyAdmin(Pageable pageable){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            List<GetPost> allPost = adminService.getPostsbyAdmin(userId, pageable);
            return new BaseResponse<>(allPost);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시물 신고 조회  API
     * [GET]  http://localhost:8080/admin/post/report
     * @return BaseResponse<?>
     */

    /**
     * 회원 정지 시키기  API
     * [POST]  http://localhost:8080/admin/user/state
     * @return BaseResponse<?>
     */

}
