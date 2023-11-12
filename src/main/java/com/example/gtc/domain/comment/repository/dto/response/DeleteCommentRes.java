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
@Schema(description = "신고 결과")
public class DeleteCommentRes {
    private String message;
    private Long commentId;
    private Long commentReportId;
    private String reportContent;
}
