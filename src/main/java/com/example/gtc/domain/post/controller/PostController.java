package com.example.gtc.domain.post.controller;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.config.BaseResponse;
import com.example.gtc.domain.post.repository.dto.request.*;
import com.example.gtc.domain.post.repository.dto.response.*;
import com.example.gtc.domain.post.service.PostServiceImpl;
import com.example.gtc.common.utils.JwtService;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.gtc.common.config.BaseResponseStatus.*;

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
            @ApiResponse(code = 200, message = "OK",response = PostRes.class),
            @ApiResponse(code = 2012, message = "이름을 입력해주세요."),
            @ApiResponse(code = 2040, message = "게시글 내용을 입력해 주세요."),
            @ApiResponse(code = 2041, message = "글자수를 확인해 주세요.")
    })
    @PostMapping("/post")
    public BaseResponse<PostRes> createPost(@RequestBody @NotNull PostWriteReq postWriteReq){
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
            PostRes postWriteRes = postService.createPost(userId, postWriteReq);
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
    @ApiOperation(value = "게시글 좋아요")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = PostRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2042, message = "이미 좋아요가 되어있습니다.")
    })
    @PostMapping("/post/{postId}/post-like")
    public BaseResponse<?> createPostLike(@PathVariable int postId){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostRes postRes = postService.createPostLike(userId,postId);
            return new BaseResponse(postRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 게시물 태그 달기 API
     * [POST]  http://localhost:8080/post/{postId}/post-tag
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "게시물 태그 달기")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = PostRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2013, message = "{nickname}은 존재하지 않는 사용자입니다.."),
            @ApiResponse(code = 2004, message = "권한이 없는 유저의 접근입니다.")
    })
    @PostMapping("/post/{postId}/post-tag")
    public BaseResponse<?> createPostTag(@PathVariable int postId,
                                         @RequestBody @NotNull PostTagReq postTagReq){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostRes postRes = postService.createPostTag(userId,postId,postTagReq);
            return new BaseResponse(postRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 게시물 신고 API
     * [POST]  http://localhost:8080/post/{postId}/post-report
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "게시물 신고")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = PostRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2013, message = "{nickname}은 존재하지 않는 사용자입니다.."),
            @ApiResponse(code = 2004, message = "권한이 없는 유저의 접근입니다.")
    })
    @PostMapping("/post/{postId}/post-report")
    public BaseResponse<?> createPostReport(@PathVariable Long postId,
                                            @RequestBody Map<String, Long> map){
        if (map.get("report-Id").equals(" ")) {
            return new BaseResponse<>(EMPTY_REPORT_ID);
        }
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostRes postRes = postService.createPostReport(userId,postId,map.get("report-Id"));
            return new BaseResponse(postRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    // 2. read

    /**
     * 게시물 조회 API (자신이 작성한 글 전체)
     * [Get]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "게시글 조회 (자신이 작성한 글 전체)")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = GetPost.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
    })
    @GetMapping("/post")
    public BaseResponse<?> getMyPosts(){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            List<GetPost> getPostlist = postService.getMyPosts(userId);
            return new BaseResponse(getPostlist);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 게시물 조회 API (하나)
     * [Get]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "게시글 조회 (하나)")
    @ApiResponses({     // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = GetPost.class)
    })
    @GetMapping("/post/{postId}")
    public BaseResponse<?> getPost(@PathVariable Long postId){
        try{
            GetPost getPost = postService.getPost(postId);
            return new BaseResponse(getPost);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 게시물 조회 API (팔로잉한 친구들의 글까지 모두)
     * [Get]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */

    // 3. update

    /**
     * 게시물 수정 API
     * [POST]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "게시물 수정")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = GetPost.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
    })
    @PostMapping("/post/{postId}")
    public BaseResponse<?> updatePosts(@PathVariable Long postId,
                                       @RequestBody Map<String, String> map){
        if (map.get("post-content").equals(" ")) {
            return new BaseResponse<>(POST_POSTS_EMPTY_CONTENT);
        }
        if (map.get("post-content").length() >= 2000) {
            return new BaseResponse<>(POST_POSTS_OVER_CONTENT);
        }
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            GetPost getPost = postService.updatePost(userId, postId, map.get("post-content"));
            return new BaseResponse(getPost);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    // 4. delete
    /**
     * 게시물 신고 취소 API
     * [DELETE]  http://localhost:8080/post/{postId}/post-report/edit
     * @return BaseResponse<?>
     */

    /**
     * 게시물 좋아요 취소 API
     * [DELETE]  http://localhost:8080/post/{postId}/post-like
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "게시글 좋아요 취소")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = PostRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
    })
    @DeleteMapping("/post/{postId}/post-like")
    public BaseResponse<?> deletePostLike(@PathVariable int postId){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostRes postRes = postService.deletePostLike(userId,postId);
            return new BaseResponse(postRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }
    /**
     * 게시물 태그 취소 API
     * [DELETE]  http://localhost:8080/post/{postId}/post-tag
     * @return BaseResponse<?>
     */
    @ApiOperation(value = "게시글 태그 취소")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = PostRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2013, message = "{nickname}은 존재하지 않는 사용자입니다.."),
            @ApiResponse(code = 2004, message = "권한이 없는 유저의 접근입니다.")
    })
    @DeleteMapping("/post/{postId}/post-tag")
    public BaseResponse<?> deletePostTag(@PathVariable int postId,
                                         @RequestBody @NotNull PostTagReq postTagReq){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostRes postRes = postService.deletePostTag(userId,postId,postTagReq);
            return new BaseResponse(postRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 게시물 삭제 API
     * [DELETE]  http://localhost:8080/post/{postId}
     * @return BaseResponse<?>
     */
//    @ApiOperation(value = "게시글 삭제")
//    @ApiResponses({  // Response Message에 대한 Swagger 설명
//            @ApiResponse(code = 200, message = "OK",response = PostRes.class),
//            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
//            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
//    })
//    @DeleteMapping("/post/{postId}")
//    public BaseResponse<?> deletePost(@PathVariable int postId){
//        try{
//            // jwt
//            Long userId = jwtService.getUserIdx();
//            PostRes postRes = postService.deletePostLike(userId,postId);
//            return new BaseResponse(postRes);
//        }catch(BaseException exception){
//            return new BaseResponse((exception.getStatus()));
//        }
//    }

}