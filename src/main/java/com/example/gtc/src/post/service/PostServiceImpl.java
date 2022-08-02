package com.example.gtc.src.post.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.post.entity.Photo;
import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.post.entity.PostLike;
import com.example.gtc.src.post.entity.PostTag;
import com.example.gtc.src.post.repository.PhotoJpaRepository;
import com.example.gtc.src.post.repository.PostJpaRepository;
import com.example.gtc.src.post.repository.PostLikeJpaRepository;
import com.example.gtc.src.post.repository.PostTagJpaRepository;
import com.example.gtc.src.post.repository.dto.request.PostTagReq;
import com.example.gtc.src.post.repository.dto.request.PostWriteReq;
import com.example.gtc.src.post.repository.dto.response.PostRes;
import com.example.gtc.src.user.entity.User;
import com.example.gtc.src.user.repository.UserJpaRepository;
import com.example.gtc.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.gtc.config.BaseResponseStatus.*;

@Service
public class PostServiceImpl implements PostService{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostJpaRepository postJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostTagJpaRepository postTagJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final JwtService jwtService;

    public PostServiceImpl(PostJpaRepository postJpaRepository, PhotoJpaRepository photoJpaRepository,
                           PostLikeJpaRepository postLikeJpaRepository, PostTagJpaRepository postTagJpaRepository, UserJpaRepository userJpaRepository, JwtService jwtService) {
        this.postJpaRepository = postJpaRepository;
        this.photoJpaRepository = photoJpaRepository;
        this.postLikeJpaRepository = postLikeJpaRepository;
        this.postTagJpaRepository = postTagJpaRepository;
        this.userJpaRepository = userJpaRepository;
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

}
