package com.example.gtc.domain.comment.service;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.global.ReportList;
import com.example.gtc.common.global.ReportListJpaRepository;
import com.example.gtc.domain.comment.entity.Comment;
import com.example.gtc.domain.comment.entity.CommentLike;
import com.example.gtc.domain.comment.entity.CommentReport;
import com.example.gtc.domain.comment.repository.CommentJpaRepository;
import com.example.gtc.domain.comment.repository.CommentLikeJpaRepository;
import com.example.gtc.domain.comment.repository.CommentReportJpaRepository;
import com.example.gtc.domain.comment.repository.dto.response.*;
import com.example.gtc.domain.post.infrastructure.entity.Post;
import com.example.gtc.domain.post.infrastructure.PostJpaRepository;
import com.example.gtc.domain.user.entity.User;
import com.example.gtc.domain.user.repository.UserJpaRepository;
import com.example.gtc.common.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.gtc.common.config.BaseResponseStatus.*;

@Service
public class CommentServiceImpl implements CommentService{

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommentJpaRepository commentJpaRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final ReportListJpaRepository reportListJpaRepository;
    private final CommentReportJpaRepository commentReportJpaRepository;
    private final JwtService jwtService;

    public CommentServiceImpl(CommentJpaRepository commentJpaRepository, CommentLikeJpaRepository commentLikeJpaRepository, UserJpaRepository userJpaRepository,
                              PostJpaRepository postJpaRepository, ReportListJpaRepository reportListJpaRepository, CommentReportJpaRepository commentReportJpaRepository, JwtService jwtService) {
        this.commentJpaRepository = commentJpaRepository;
        this.commentLikeJpaRepository = commentLikeJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.postJpaRepository = postJpaRepository;
        this.reportListJpaRepository = reportListJpaRepository;
        this.commentReportJpaRepository = commentReportJpaRepository;
        this.jwtService = jwtService;
    }

    @Override
    public PostCommentRes createComment(Long userId, Long postId, String commentContent) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<Post> post = postJpaRepository.findById(postId);

        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        try{
            Comment comment = Comment.toEntity(user.get(),post.get(), commentContent);
            commentJpaRepository.save(comment);
            return new PostCommentRes(comment.getCommentId(), comment.getPost().getPostId(),
                    comment.getUser().getUserId(), comment.getCommentContent(),
                    comment.getCommentCreateTime(), comment.getState());
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public PostSuccessRes createCommentLike(Long userId, Long commentId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<Comment> comment = commentJpaRepository.findById(commentId);

        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        if(comment.isEmpty()){
            throw new BaseException(EMPTY_COMMENT);
        }
        try{
            CommentLike commentLike = CommentLike.toEntity(user.get(), comment.get());
            commentLikeJpaRepository.save(commentLike);
            return new PostSuccessRes("댓글 좋아요가 눌렸습니다.");
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    @Override
    public PostCommentReportRes createCommentReport(Long userId, Long commentId, Long reportId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<Comment> comment = commentJpaRepository.findById(commentId);
        Optional<ReportList> report = reportListJpaRepository.findById(reportId);

        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        if(comment.isEmpty()){
            throw new BaseException(EMPTY_COMMENT);
        }
        if(report.isEmpty()){
            throw new BaseException(EMPTY_REPORT);
        }
        try {
            CommentReport commentReport = CommentReport.toEntity(comment.get(), user.get(), report.get());
            commentReportJpaRepository.save(commentReport);
            return new PostCommentReportRes("댓글 신고가 되었습니다.", commentId,
                    reportId,report.get().getReportContent());

        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    @Override
    public PostSuccessRes deleteCommentLike(Long userId, Long commentlikeId) throws BaseException {
        Optional<CommentLike> commentLike = commentLikeJpaRepository.findById(commentlikeId);

        if(commentLike.isEmpty()){
            throw new BaseException(EMPTY_COMMENT_LIKE);
        }
        try {
            commentLikeJpaRepository.delete(commentLike.get());
            return new PostSuccessRes("댓글 좋아요가 취소되었습니다.");
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    @Override
    public PostSuccessRes deleteCommentReport(Long userId, Long commentReportId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<CommentReport> commentReport = commentReportJpaRepository.findById(commentReportId);

        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        if(commentReport.isEmpty()){
            throw new BaseException(EMPTY_REPORT);
        }
        try {
            if(commentReport.get().getUser() == user.get()){
                commentReportJpaRepository.delete(commentReport.get());
                return new PostSuccessRes("댓글 신고가 취소되었습니다.");
            }else{
                throw new BaseException(INVALID_REPORT_USER);
            }
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public PostSuccessRes deleteComment(Long userId, Long commentId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        // 댓글 삭제
        Optional<Comment> comment =commentJpaRepository.findById(commentId);
        if(comment.isEmpty()){
            throw new BaseException(EMPTY_COMMENT);
        }
        // 댓글 좋아요 삭제
        List<CommentLike> commentLikeList = commentLikeJpaRepository.findAll();
        // 댓글 신고 삭제
        List<CommentReport> commentReportList = commentReportJpaRepository.findAll();
        try {
            for(int i=0; i<commentLikeList.size(); i++){
                if(commentLikeList.get(i).getUser() == user.get() && commentLikeList.get(i).getComment() == comment.get()){
                    commentLikeJpaRepository.delete(commentLikeList.get(i));
                }
            }
            for(int i=0; i<commentReportList.size(); i++){
                if(commentReportList.get(i).getUser() == user.get() && commentLikeList.get(i).getComment() == comment.get()){
                    commentReportJpaRepository.delete(commentReportList.get(i));
                }
            }
            commentJpaRepository.delete(comment.get());
            return new PostSuccessRes("댓글이 삭제되었습니다.");
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
