package com.example.gtc.src.comment.controller;

import com.example.gtc.config.BaseException;
import com.example.gtc.config.BaseResponse;
import com.example.gtc.src.comment.repository.dto.response.*;
import com.example.gtc.src.comment.service.CommentServiceImpl;
import com.example.gtc.src.post.repository.dto.response.PostRes;
import com.example.gtc.utils.JwtService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.gtc.config.BaseResponseStatus.*;

@RestController
@Api(tags = "댓글 API")
public class CommentController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommentServiceImpl commentService;
    private final JwtService jwtService;

    public CommentController(CommentServiceImpl commentService, JwtService jwtService) {
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    /**
     * 댓글 작성 API
     * [POST]  http://localhost:8080/post/{postId}/comment
     * @return BaseResponse<PostWriteRes>
     */
    @ApiOperation(value = "댓글 작성")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK", response = PostCommentRes.class),
            @ApiResponse(code = 2012, message = "이름을 입력해주세요."),
            @ApiResponse(code = 2050, message = "댓글 내용을 입력해 주세요."),
            @ApiResponse(code = 2041, message = "글자수를 확인해 주세요.")
    })
    @PostMapping("/post/{postId}/comment")
    public BaseResponse<?> createComment(@PathVariable Long postId,
                                         @RequestBody Map<String, String> map) {
        if (map.get("comment-content").equals(" ")) {
            return new BaseResponse<>(POST_COMMENTS_EMPTY_CONTENT);
        }
        if (map.get("comment-content").length() >= 1000) {
            return new BaseResponse<>(POST_COMMENTS_OVER_CONTENT);
        }
        try {
            // jwt
            Long userId = jwtService.getUserIdx();
            PostCommentRes postCommentRes = commentService.createComment(userId, postId, map.get("comment-content"));
            return new BaseResponse(postCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 댓글 좋아요 API
     * [POST]  http://localhost:8080/post/{postId}/comment/like
     * @return BaseResponse<PostWriteRes>
     */
    @ApiOperation(value = "댓글 좋아요")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK", response = PostSuccessRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2006, message = "존재하지 않는 댓글입니다."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
    })
    @PostMapping("/post/{postId}/comment/like")
    public BaseResponse<?> createCommentLike(@PathVariable Long postId,
                                             @RequestBody Map<String, Long> map) {
        if (map.get("commentId").equals(" ")) {
            return new BaseResponse<>(POST_COMMENTS_EMPTY_CONTENT_ID);
        }
        try {
            // jwt
            Long userId = jwtService.getUserIdx();
            PostSuccessRes postSuccessRes = commentService.createCommentLike(userId, map.get("commentId"));
            return new BaseResponse(postSuccessRes);
        } catch (BaseException exception) {
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 댓글 신고 API
     * [POST]  http://localhost:8080/post/{postId}/comment/report
     * @return BaseResponse<PostWriteRes>
     */
    @ApiOperation(value = "댓글 신고")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK", response = PostRes.class),
            @ApiResponse(code = 2012, message = "이름을 입력해주세요.")
    })
    @PostMapping("/post/{postId}/comment/report")
    public BaseResponse<?> createCommentReport(@PathVariable Long postId,
                                               @RequestBody Map<String, Long> map) {
        if (map.get("commentId").equals(" ")) {
            return new BaseResponse<>(POST_COMMENTS_EMPTY_CONTENT_ID);
        }
        if (map.get("report-Id").equals(" ")) {
            return new BaseResponse<>(EMPTY_REPORT_ID);
        }
        try {
            // jwt
            Long userId = jwtService.getUserIdx();
            PostCommentReportRes postCommentReportRes = commentService.createCommentReport(userId, map.get("commentId"), map.get("report-Id"));
            return new BaseResponse(postCommentReportRes);
        } catch (BaseException exception) {
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 댓글 전체 보기 API
     * [GET]  http://localhost:8080/{postId}/comment
     * @return BaseResponse<PostWriteRes>
     */


    /**
     * 댓글 좋아요 취소 API
     * [DELETE]  http://localhost:8080/post/{postId}/comment/like
     * @return BaseResponse<PostWriteRes>
     */
    @ApiOperation(value = "댓글 좋아요 취소")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK", response = PostSuccessRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2006, message = "존재하지 않는 댓글입니다."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
    })
    @DeleteMapping("/post/{postId}/comment/like")
    public BaseResponse<?> deleteCommentLike(@PathVariable Long postId,
                                             @RequestBody Map<String, Long> map) {
        if (map.get("comment-like-Id").equals(" ")) {
            return new BaseResponse<>(POST_COMMENTS_EMPTY_CONTENT_LIKE_ID);
        }
        try {
            // jwt
            Long userId = jwtService.getUserIdx();
            PostSuccessRes postSuccessRes = commentService.deleteCommentLike(userId, map.get("comment-like-Id"));
            return new BaseResponse(postSuccessRes);
        } catch (BaseException exception) {
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 댓글 신고 취소 API
     * [DELETE]  http://localhost:8080/post/{postId}/comment/report
     * @return BaseResponse<PostWriteRes>
     */
    @ApiOperation(value = "댓글 좋아요 취소")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK", response = PostSuccessRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2006, message = "존재하지 않는 댓글입니다."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
    })
    @DeleteMapping("/post/{postId}/comment/report")
    public BaseResponse<?> deleteCommentReport(@PathVariable Long postId,
                                             @RequestBody Map<String, Long> map) {
        if (map.get("comment-report-Id").equals(" ")) {
            return new BaseResponse<>(EMPTY_REPORT);
        }
        try {
            // jwt
            Long userId = jwtService.getUserIdx();
            PostSuccessRes postSuccessRes = commentService.deleteCommentReport(userId, map.get("comment-report-Id"));
            return new BaseResponse(postSuccessRes);
        } catch (BaseException exception) {
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 댓글 하나 삭제 API
     * [DELETE]  http://localhost:8080/post/{postId}/comment
     * @return BaseResponse<PostWriteRes>
     */
    @ApiOperation(value = "댓글 하나 삭제")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK", response = PostSuccessRes.class),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2006, message = "존재하지 않는 댓글입니다."),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다.")
    })
    @DeleteMapping("/post/{postId}/comment")
    public BaseResponse<?> deleteComment(@PathVariable Long postId,
                                         @RequestBody Map<String, Long> map) {
        if (map.get("comment-Id").equals(" ")) {
            return new BaseResponse<>(POST_COMMENTS_EMPTY_CONTENT_ID);
        }
        try {
            // jwt
            Long userId = jwtService.getUserIdx();
            PostSuccessRes postSuccessRes = commentService.deleteComment(userId, map.get("comment-Id"));
            return new BaseResponse(postSuccessRes);
        } catch (BaseException exception) {
            return new BaseResponse((exception.getStatus()));
        }
    }

    /**
     * 댓글 전체 삭제 API
     * [DELETE]  http://localhost:8080/post
     * @return BaseResponse<PostWriteRes>
     */

}
