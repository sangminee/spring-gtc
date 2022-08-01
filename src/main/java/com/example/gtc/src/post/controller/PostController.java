package com.example.gtc.src.post.controller;

import com.example.gtc.config.BaseException;
import com.example.gtc.config.BaseResponse;
import com.example.gtc.src.post.repository.dto.request.PostWriteReq;
import com.example.gtc.src.post.repository.dto.response.PostWriteRes;
import com.example.gtc.src.post.service.PostServiceImpl;
import com.example.gtc.src.user.repository.dto.request.PostUserPhoneJoinReq;
import com.example.gtc.src.user.repository.dto.response.PostJoinUserRes;
import com.example.gtc.src.user.repository.dto.response.PostLoginRes;
import com.example.gtc.utils.JwtService;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Queue;

import static com.example.gtc.config.BaseResponseStatus.*;
import static com.example.gtc.utils.ValidationRegex.isRegexPassword;
import static com.example.gtc.utils.ValidationRegex.isRegexPhoneNumber;

@RestController
@Api(tags ="게시글 API")
public class PostController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostServiceImpl postService;
    private final JwtService jwtService;

    public PostController(PostServiceImpl postService, JwtService jwtService) {
        this.postService = postService;
        this.jwtService = jwtService;
    }

    // 1. create
    /**
     * 게시물 작성 API
     * [POST]  http://localhost:8080/post
     * @return BaseResponse<PostWriteRes>
     */
    @ApiOperation(value = "게시글 작성")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = PostWriteRes.class),
            @ApiResponse(code = 2012, message = "이름을 입력해주세요."),
            @ApiResponse(code = 2040, message = "게시글 내용을 입력해 주세요."),
            @ApiResponse(code = 2041, message = "글자수를 확인해 주세요.")
    })
    @PostMapping("/post")
    public BaseResponse<PostWriteRes> createPost(@RequestBody @NotNull PostWriteReq postWriteReq){
        // validation
        if(postWriteReq.getPostContent().equals("")){
            return new BaseResponse<>(POST_POSTS_EMPTY_CONTENT);
        }

        int strLength = postWriteReq.getPostContent().length();
        System.out.println("글자수 : "+ strLength);
        if(strLength > 1000){
            return new BaseResponse<>(POST_POSTS_OVER_CONTENT);
        }

        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostWriteRes postWriteRes = postService.createPost(userId, postWriteReq);
            return new BaseResponse(postWriteRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 게시물 좋아요 API
     * [POST]  http://localhost:8080/post/{postId}/post-like
     * @return BaseResponse<?>
     */

    /**
     * 게시물 신고 API
     * [POST]  http://localhost:8080/post/{postId}/post-report
     * @return BaseResponse<?>
     */

    // 2. read
    /**
     * 게시물 조회 API
     * [Get]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */

    // 3. update

    /**
     * 게시물 수정 API
     * [POST]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */

    /**
     * 게시물 신고 취소 API
     * [POST]  http://localhost:8080/post/{postId}/post-report/edit
     * @return BaseResponse<?>
     */

    /**
     * 게시물 좋아요 취소 API
     * [POST]  http://localhost:8080/post/{postId}/post-like/edit
     * @return BaseResponse<?>
     */

    // 4. delete
    /**
     * 게시물 삭제 API
     * [DELETE]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */
}
