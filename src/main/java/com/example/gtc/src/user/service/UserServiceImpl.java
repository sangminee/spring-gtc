package com.example.gtc.src.user.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.config.secret.Secret;
import com.example.gtc.src.user.entity.User;
import com.example.gtc.src.user.repository.UserJpaRepository;
import com.example.gtc.src.user.repository.dto.response.DeleteUserRes;
import com.example.gtc.src.user.repository.dto.response.GetUserProfileRes;
import com.example.gtc.src.user.repository.dto.response.PostEditUserRes;
import com.example.gtc.src.user.repository.dto.request.PostUserEmailJoinReq;
import com.example.gtc.src.user.repository.dto.response.PostJoinUserRes;
import com.example.gtc.src.user.repository.dto.request.PostUserPhoneJoinReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginEmailReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginNicknameReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginPhoneReq;
import com.example.gtc.src.user.repository.dto.response.PostLoginRes;
import com.example.gtc.utils.AES128;
import com.example.gtc.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.gtc.config.BaseResponseStatus.*;

@Service
public class UserServiceImpl implements UserService{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserJpaRepository userJpaRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserJpaRepository userJpaRepository, JwtService jwtService) {
        this.userJpaRepository = userJpaRepository;
        this.jwtService = jwtService;
    }

    @Override
    public GetUserProfileRes retrieveUserProfile(String nickname, Long userId) throws BaseException{
        Optional<User> user = userJpaRepository.findByNickname(nickname);
        if(user.get().getUserId().equals(userId)){
            GetUserProfileRes getUserProfileRes = new GetUserProfileRes();
            getUserProfileRes.setNickname(user.get().getNickname());
            getUserProfileRes.setName(user.get().getName());
            return getUserProfileRes;
        }else{
            throw new BaseException(INVALID_USER_JWT);
        }
    }

    @Override
    public PostJoinUserRes createUserPhone(PostUserPhoneJoinReq postUserPhoneReq) throws BaseException {
        // 사용자 이름 중복 검사
        this.checkUserNickname(postUserPhoneReq.getNickname());
        // 핸드폰 번호 중복 검사
        this.checkUserPhone(postUserPhoneReq.getPhone());

        // 비밀번호 암호화
        String password;
        try{
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserPhoneReq.getPassword());
            postUserPhoneReq.setPassword(password);
        }
        catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        // jwt 발급
        try{
            User user = User.toEntity(postUserPhoneReq);

            if(user.getAgree().equals("N")){
                user.setState(3);
            }else{
                user.setState(0);
            }
            userJpaRepository.save(user);

            return new PostJoinUserRes("회원가입이 완료되었습니다.", user.getNickname());
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    @Override
    public PostJoinUserRes createUserEmail(PostUserEmailJoinReq postUserReq) throws BaseException {
        return null;
    }

    @Override
    public void checkUserPhone(String phone) throws BaseException {
        Optional<User> checkPhone = userJpaRepository.findByPhone(phone);
        if(!checkPhone.isEmpty()){
            throw new BaseException(DUPLICATED_PHONE);
        }
    }

    @Override
    public void checkUserNickname(String nickname) throws BaseException{
        Optional<User> checkUserNickname = userJpaRepository.findByNickname(nickname);
        if(!checkUserNickname.isEmpty()){
            throw new BaseException(DUPLICATED_NICKNAME);
        }
    }

    @Override
    public PostLoginRes logInPhone(PostLoginPhoneReq postLoginReq) throws BaseException{
        Optional<User> user = userJpaRepository.findByPhone(postLoginReq.getPhone());
        if(user.isEmpty()){
            throw new BaseException(FAILED_TO_LOGIN);
        }

        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.get().getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(postLoginReq.getPassword().equals(password)){
            String jwt = jwtService.createJwt(user.get().getUserId());

            return new PostLoginRes("로그인되었습니다.", user.get().getNickname(), jwt,
                    user.get().getAgree(),user.get().getState());
        }else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    @Override
    public PostLoginRes logInEmail(PostLoginEmailReq postLoginReq) throws BaseException {
        return null;
    }

    @Override
    public PostLoginRes logInNickname(PostLoginNicknameReq postLoginReq) throws BaseException {
        return null;
    }

    @Override
    public PostEditUserRes editPassword(String nickname, String password, Long userId) throws BaseException {
        Optional<User> user = userJpaRepository.findByNickname(nickname);
        if(user.get().getUserId().equals(userId)){
            // 비밀번호 암호화
            String newPassword;
            try{
                newPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(password);
            }
            catch (Exception ignored) {
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }

            if(!user.get().getPassword().equals(newPassword)){ // 기존의 비밀번호와 같지 않다면
                // 비밀번호 변경
                user.get().setPassword(newPassword);
                userJpaRepository.save(user.get());
                return new PostEditUserRes("비밀번호가 변경되었습니다.", user.get().getNickname(),
                        user.get().getName(),password,
                        user.get().getUserImgUrl(),user.get().getWebsite(),
                        user.get().getBio(), user.get().getNameUpdateCount(), user.get().getNicknameUpdateCount(),
                        user.get().getAgree(), user.get().getState());

            }else{
                throw new BaseException(FAILED_TO_PASSWORD);
            }

        }else{
            throw new BaseException(INVALID_USER_JWT);
        }
    }

    @Override
    public PostEditUserRes editUserImg(String nickname, String userImgUrl) throws BaseException {
        return null;
    }

    @Override
    public PostEditUserRes editWebsite(String nickname, String website) throws BaseException {
        return null;
    }

    @Override
    public PostEditUserRes editBio(String nickname, String bio) throws BaseException {
        return null;
    }

    @Override
    public PostEditUserRes editName(String nickname, String name) throws BaseException {
        return null;
    }

    @Override
    public PostEditUserRes editNickname(String nickname, String changeNickname, Long userId) throws BaseException {
        Optional<User> user = userJpaRepository.findByNickname(nickname);
        if(user.get().getUserId().equals(userId)){
            if(!user.get().getNickname().equals(changeNickname)){
                // 사용자 이름 중복 검사
                this.checkUserNickname(changeNickname);

                // 2 이상일 경우 14일 동안 변경 불가
                if(user.get().getNicknameUpdateCount() <=2){
                    user.get().setNicknameUpdateCount(user.get().getNicknameUpdateCount()+1);
                    user.get().setNickname(changeNickname);
                    userJpaRepository.save(user.get());
                    // 비번
                    String password;
                    try {
                        password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.get().getPassword());
                    } catch (Exception ignored) {
                        throw new BaseException(PASSWORD_DECRYPTION_ERROR);
                    }
                    return new PostEditUserRes("사용자 이름이 변경되었습니다.", user.get().getNickname(),
                            user.get().getName(),password,
                            user.get().getUserImgUrl(),user.get().getWebsite(),
                            user.get().getBio(), user.get().getNameUpdateCount(), user.get().getNicknameUpdateCount(),
                            user.get().getAgree(), user.get().getState());
                }else{
                    throw new BaseException(FAILED_TO_NICKNAME_COUNT);
                }
            }else{
                throw new BaseException(FAILED_TO_NICKNAME);
            }
        }else{
            throw new BaseException(INVALID_USER_JWT);
        }
    }

    @Override
    public PostEditUserRes editState(String nickname, int state) throws BaseException {
        return null;
    }

    @Override
    public DeleteUserRes deleteUser(String nickname, String password, Long userId) throws BaseException {
        Optional<User> user = userJpaRepository.findByNickname(nickname);
        if(user.get().getUserId().equals(userId)){
            // 비밀번호 암호화
            String checkPassword;
            try{
                checkPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(password);
            }
            catch (Exception ignored) {
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }

            if(user.get().getPassword().equals(checkPassword)){
                userJpaRepository.delete(user.get());
                return new DeleteUserRes("계정이 삭제되었습니다.");

            }else{
                throw new BaseException(FAILED_TO_USER_DELETE);
            }
        }else{
            throw new BaseException(INVALID_USER_JWT);
        }
    }
}