package com.example.gtc.domain.post.service;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.global.ReportList;
import com.example.gtc.common.global.ReportListJpaRepository;
import com.example.gtc.domain.post.entity.*;
import com.example.gtc.domain.post.repository.*;
import com.example.gtc.domain.post.repository.dto.request.PostTagReq;
import com.example.gtc.domain.post.repository.dto.request.PostWriteReq;
import com.example.gtc.domain.post.repository.dto.response.GetPost;
import com.example.gtc.domain.post.repository.dto.response.PostRes;
import com.example.gtc.domain.user.entity.User;
import com.example.gtc.domain.user.repository.UserJpaRepository;
import com.example.gtc.common.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.gtc.common.config.BaseResponseStatus.*;

@Service
public class PostServiceImpl implements PostService{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostJpaRepository postJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostTagJpaRepository postTagJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ReportListJpaRepository reportListJpaRepository;
    private final PostReportJpaRepository postReportJpaRepository;
    private final JwtService jwtService;

    public PostServiceImpl(PostJpaRepository postJpaRepository, PhotoJpaRepository photoJpaRepository,
                           PostLikeJpaRepository postLikeJpaRepository, PostTagJpaRepository postTagJpaRepository, UserJpaRepository userJpaRepository, ReportListJpaRepository reportListJpaRepository, PostReportJpaRepository postReportJpaRepository, JwtService jwtService) {
        this.postJpaRepository = postJpaRepository;
        this.photoJpaRepository = photoJpaRepository;
        this.postLikeJpaRepository = postLikeJpaRepository;
        this.postTagJpaRepository = postTagJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.reportListJpaRepository = reportListJpaRepository;
        this.postReportJpaRepository = postReportJpaRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public PostRes createPost(Long userId, PostWriteReq postWriteReq) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(!user.isEmpty()){
            Post post = Post.toEntity(postWriteReq,user.get());
            postJpaRepository.save(post);
            for(int i=0; i< postWriteReq.getPhotoCount(); i++){
                Photo photo = Photo.toEntity(postWriteReq.getQueue().poll(),post);
                photoJpaRepository.save(photo);
            }
            return new PostRes("게시글 작성이 완료되었습니다.");

        }else{
            throw new BaseException(INVALID_USER_JWT);
        }
    }

    @Override
    public PostRes createPostTag(Long userId, int postId, PostTagReq postTagReq) throws BaseException {
        Optional<Post> post = postJpaRepository.findById((long) postId);
        List<PostTag> allPostTag = postTagJpaRepository.findAll();

        if(post.get().getUser().getUserId() == userId){
            for(String tagUser : postTagReq.getNickname()){
                Optional<User> user = userJpaRepository.findByNickname(tagUser);
                if(!user.isEmpty()){
                    if(allPostTag.size() == 0){
                        PostTag postTag = PostTag.toEntity(post.get(), user.get());
                        postTagJpaRepository.save(postTag);
                    }else{
                        for(int i=0; i<allPostTag.size(); i++){
                            if(allPostTag.get(i).getPost().getPostId() != postId
                                    && allPostTag.get(i).getUser().getUserId() != user.get().getUserId() ){

                                PostTag postTag = PostTag.toEntity(post.get(), user.get());
                                postTagJpaRepository.save(postTag);
                            }
                        }
                    }
                }else{
                    return new PostRes(tagUser + "는 존재하지 않는 사용자입니다.");
                }
            }
            return new PostRes("태그가완료되었습니다.");
        }else{
            throw new BaseException(INVALID_USER_JWT);
        }
    }

    @Override
    public PostRes createPostLike(Long userId, int postId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<Post> post = postJpaRepository.findById((long) postId);
        List<PostLike> all = postLikeJpaRepository.findAll();

        if(all.size() == 0){
            PostLike postLike = PostLike.toEntity(post.get(), user.get());
            postLikeJpaRepository.save(postLike);
            return new PostRes("좋아요가 표시되었습니다.");
        }else{
            for(int i=0; i<all.size(); i++){
                if(all.get(i).getPost().getPostId() != postId
                        && all.get(i).getUser().getUserId() != userId ){

                    PostLike postLike = PostLike.toEntity(post.get(), user.get());
                    postLikeJpaRepository.save(postLike);
                    return new PostRes("좋아요가 표시되었습니다.");
                }
            }
        }
        throw new BaseException(POST_POSTS_EXISTENT_LIKE);
    }

    @Override
    public PostRes createPostReport(Long userId, Long postId, Long reportId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<Post> post = postJpaRepository.findById(postId);
        Optional<ReportList> reportList = reportListJpaRepository.findById(reportId);

        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        if(post.isEmpty()){
            throw new BaseException(EMPTY_POST);
        }
        if(reportList.isEmpty()){
            throw new BaseException(EMPTY_REPORT);
        }

        try {
            PostReport postReport =PostReport.toEntity(post.get(),user.get(),reportList.get());
            postReportJpaRepository.save(postReport);

            post.get().setState(2);
            postJpaRepository.save(post.get());

            return new PostRes("게시물이 신고되었습니다.");
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public PostRes deletePostLike(Long userId, int postId) throws BaseException {
        List<PostLike> all = postLikeJpaRepository.findAll();

        if(all.size() == 0){
            return new PostRes("좋아요가 표시된 게시물이 하나도 없습니다.");
        }else{
            for(int i=0; i<all.size(); i++){
                if(all.get(i).getPost().getPostId() == postId
                        && all.get(i).getUser().getUserId() == userId ){
                    postLikeJpaRepository.delete(all.get(i));
                    return new PostRes("좋아요가 취소되었습니다.");
                }
            }
        }
        throw new BaseException(REQUEST_ERROR);
    }

    @Override
    public PostRes deletePostTag(Long userId, int postId, PostTagReq postTagReq) throws BaseException {
        List<PostTag> allPostTag = postTagJpaRepository.findAll();
        Optional<Post> post = postJpaRepository.findById((long) postId);

        if(post.get().getUser().getUserId() == userId){
            for(String tagUser : postTagReq.getNickname()) {
                Optional<User> user = userJpaRepository.findByNickname(tagUser);
                if(!user.isEmpty()){
                    if(allPostTag.size() != 0){
                        for(int i=0; i<allPostTag.size(); i++){
                            if(allPostTag.get(i).getPost().getPostId() == postId
                                    && allPostTag.get(i).getUser().getUserId() == user.get().getUserId() ){
                                postTagJpaRepository.delete(allPostTag.get(i));
                            }
                        }
                        return new PostRes("태그 삭제가 완료되었습니다.");
                    }else{
                        return new PostRes("태그 테이블에 값이 존재하지 않습니다.");
                    }
                }else{
                    return new PostRes(tagUser + "는 존재하지 않는 사용자입니다.");
                }
            }
        }
        throw new BaseException(INVALID_USER_JWT);
    }

    @Override
    public List<GetPost> getMyPosts(Long userId) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        List<Post> allPostList = postJpaRepository.findAll();

        try {
            List<GetPost> getPosts = new ArrayList<>();

            for(int i=0; i<allPostList.size(); i++){
                if(allPostList.get(i).getUser().equals(user.get())){
                    getPosts.add(this.getPost(allPostList.get(i).getPostId()));
                }
            }
            return getPosts;

        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public GetPost getPost(Long postId) throws BaseException {
        Optional<Post> post = postJpaRepository.findById(postId);

        List<PostLike> allPostLike = postLikeJpaRepository.findAll();
        List<Photo> allPostPhoto = photoJpaRepository.findAll();

        if(post.isEmpty()){
            throw new BaseException(EMPTY_POST);
        }

        try {
            int likeSum = 0;
            for(int i=0; i<allPostLike.size(); i++){
                if(allPostLike.get(i).getPost().equals(post.get())){
                    likeSum++;
                }
            }
            ArrayList<String> photoUrlList = new ArrayList<>();

            for(int i=0; i < allPostPhoto.size(); i++){
                if(allPostPhoto.get(i).getPost().equals(post.get())){
                    photoUrlList.add(allPostPhoto.get(i).getPhotoUrl());
                }
            }

            return new GetPost(post.get().getPostId(),
                    post.get().getUser().getUserId(),
                    post.get().getUser().getNickname(),
                    post.get().getPostContent(),
                    post.get().getPostCreateTime(),
                    post.get().getPostUpdateTime(),
                    post.get().getState(),
                    photoUrlList, likeSum);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public GetPost updatePost(Long userId, Long postId, String content) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        Optional<Post> post = postJpaRepository.findById(postId);

        List<Photo> allPostPhoto = photoJpaRepository.findAll();
        List<PostLike> allPostLike = postLikeJpaRepository.findAll();

        if(user.isEmpty()){
            throw new BaseException(INVALID_USER_JWT);
        }
        try {

            post.get().setPostContent(content);
            postJpaRepository.save(post.get());

            int likeSum = 0;
            for(int i=0; i<allPostLike.size(); i++){
                if(allPostLike.get(i).getPost().equals(post.get())){
                    likeSum++;
                }
            }
            ArrayList<String> photoUrlList = new ArrayList<String>();
            for(int i=0; i<allPostPhoto.size(); i++){
                if(allPostPhoto.get(i).getPost().equals(post.get())){
                    photoUrlList.add(allPostPhoto.get(i).getPhotoUrl());
                }
            }
            return new GetPost(post.get().getPostId(),
                    post.get().getUser().getUserId(),
                    post.get().getUser().getNickname(),
                    post.get().getPostContent(),
                    post.get().getPostCreateTime(),
                    post.get().getPostUpdateTime(),
                    post.get().getState(),
                    photoUrlList, likeSum);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
