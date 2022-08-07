package com.example.gtc.src.comment.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "신고 결과")
public class PostCommentReportRes {
    private String message;
    private Long commentId;
    private Long reportListId;
    private String reportContent;
}
