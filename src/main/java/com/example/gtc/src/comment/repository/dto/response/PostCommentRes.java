package com.example.gtc.src.comment.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "댓글 달기")
public class PostCommentRes {
    private Long commentId;
    private Long postId;
    private Long userId;

    private String commentContent;
    private LocalDateTime commentCreateTime;
    private int state;
}
