package com.example.gtc.src.post.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.post.entity.Photo;
import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.post.repository.PhotoJpaRepository;
import com.example.gtc.src.post.repository.PostJpaRepository;
import com.example.gtc.src.post.repository.dto.request.PostWriteReq;
import com.example.gtc.src.post.repository.dto.response.PostWriteRes;
import com.example.gtc.src.user.entity.User;
import com.example.gtc.src.user.repository.UserJpaRepository;
import com.example.gtc.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.gtc.config.BaseResponseStatus.INVALID_USER_JWT;

@Service
public class PostServiceImpl implements PostService{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostJpaRepository postJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final JwtService jwtService;

    public PostServiceImpl(PostJpaRepository postJpaRepository, PhotoJpaRepository photoJpaRepository, UserJpaRepository userJpaRepository, JwtService jwtService) {
        this.postJpaRepository = postJpaRepository;
        this.photoJpaRepository = photoJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public PostWriteRes createPost(Long userId, PostWriteReq postWriteReq) throws BaseException {
        Optional<User> user = userJpaRepository.findByUserId(userId);
        if(!user.isEmpty()){
            Post post = Post.toEntity(postWriteReq,user.get());
            postJpaRepository.save(post);
            for(int i=0; i< postWriteReq.getPhotoCount(); i++){
                Photo photo = Photo.toEntity(postWriteReq.getQueue().poll(),post);
                photoJpaRepository.save(photo);
            }
            return new PostWriteRes("게시글 작성이 완료되었습니다.");

        }else{
            throw new BaseException(INVALID_USER_JWT);
        }
    }

    @Override
    public void createPhoto(String url,Post post) throws BaseException {
//        Photo photo = Photo.toEntity(url,post);
//        photoJpaRepository.save(photo);
    }
}
