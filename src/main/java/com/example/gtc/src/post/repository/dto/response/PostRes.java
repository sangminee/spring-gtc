package com.example.gtc.src.post.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "게시글 작성/ 게시글 좋아요/ 댓글 달기")
public class PostRes {
    private String message;
}
