package com.example.gtc.src.admin.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.global.ReportListJpaRepository;
import com.example.gtc.src.comment.repository.CommentJpaRepository;
import com.example.gtc.src.comment.repository.CommentLikeJpaRepository;
import com.example.gtc.src.comment.repository.CommentReportJpaRepository;
import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.post.repository.PhotoJpaRepository;
import com.example.gtc.src.post.repository.PostJpaRepository;
import com.example.gtc.src.post.repository.PostLikeJpaRepository;
import com.example.gtc.src.post.repository.PostTagJpaRepository;
import com.example.gtc.src.post.repository.dto.response.GetPost;
import com.example.gtc.src.post.service.PostServiceImpl;
import com.example.gtc.src.user.entity.User;
import com.example.gtc.src.user.repository.UserJpaRepository;
import com.example.gtc.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.gtc.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.gtc.config.BaseResponseStatus.INVALID_USER_JWT;

@Service
public class AdminServiceImpl implements AdminService{

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommentJpaRepository commentJpaRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final CommentReportJpaRepository commentReportJpaRepository;

    private final PostJpaRepository postJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostTagJpaRepository postTagJpaRepository;

    private final UserJpaRepository userJpaRepository;
    private final ReportListJpaRepository reportListJpaRepository;
    private final JwtService jwtService;

    private final PostServiceImpl postService;

    public AdminServiceImpl(CommentJpaRepository commentJpaRepository, CommentLikeJpaRepository commentLikeJpaRepository,
                            PostLikeJpaRepository postLikeJpaRepository, UserJpaRepository userJpaRepository, PostJpaRepository postJpaRepository, ReportListJpaRepository reportListJpaRepository, CommentReportJpaRepository commentReportJpaRepository, PhotoJpaRepository photoJpaRepository, PostTagJpaRepository postTagJpaRepository, JwtService jwtService, PostServiceImpl postService) {
        this.commentJpaRepository = commentJpaRepository;
        this.commentLikeJpaRepository = commentLikeJpaRepository;
        this.postLikeJpaRepository = postLikeJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.postJpaRepository = postJpaRepository;
        this.reportListJpaRepository = reportListJpaRepository;
        this.commentReportJpaRepository = commentReportJpaRepository;
        this.photoJpaRepository = photoJpaRepository;
        this.postTagJpaRepository = postTagJpaRepository;
        this.jwtService = jwtService;
        this.postService = postService;
    }

    @Override
    public List<User> getUsersbyAdmin(Long userId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(user.isEmpty() && user.get().getUserType()==0){
            throw new BaseException(INVALID_USER_JWT);
        }

        try{
            List<User> allUser = userJpaRepository.findAll();
            return allUser;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public List<GetPost> getPostsbyAdmin(Long userId, Pageable pageable) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(user.isEmpty() && user.get().getUserType()==0){
            throw new BaseException(INVALID_USER_JWT);
        }
        List<Post> allPostList = postJpaRepository.findAll();

        try{
            List<GetPost> getPosts = new ArrayList<>();
            for(int i=0; i<allPostList.size(); i++){
                getPosts.add(postService.getPost(allPostList.get(i).getPostId()));
            }
            return getPosts;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
