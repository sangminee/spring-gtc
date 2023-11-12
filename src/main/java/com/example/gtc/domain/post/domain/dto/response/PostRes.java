package com.example.gtc.domain.post.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 작성/ 게시글 좋아요/ 댓글 달기")
public class PostRes {
    private String message;
}
