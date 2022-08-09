package com.example.gtc.src.comment.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.comment.repository.dto.response.PostCommentReportRes;
import com.example.gtc.src.comment.repository.dto.response.PostCommentRes;
import com.example.gtc.src.comment.repository.dto.response.PostSuccessRes;

public interface CommentService {
    PostCommentRes createComment(Long userId, Long postId, String commentContent) throws BaseException;
    PostSuccessRes createCommentLike(Long userId, Long commentId) throws BaseException;
    PostCommentReportRes createCommentReport(Long userId, Long commentId, Long reportId) throws BaseException;

    PostSuccessRes deleteCommentLike(Long userId, Long commentlikeId) throws BaseException;
    PostSuccessRes deleteComment(Long userId, Long commentId) throws BaseException;

    PostSuccessRes deleteCommentReport(Long userId, Long commentReportId)  throws BaseException;
}
