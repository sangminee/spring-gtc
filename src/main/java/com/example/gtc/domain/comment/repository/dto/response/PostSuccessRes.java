package com.example.gtc.domain.comment.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "댓글 좋아요, 댓글 신고 응답")
public class PostSuccessRes {
    private String message;
}
