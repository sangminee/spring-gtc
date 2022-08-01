package com.example.gtc.src.user.controller;

import com.example.gtc.config.*;
import com.example.gtc.src.user.repository.dto.response.DeleteUserRes;
import com.example.gtc.src.user.repository.dto.response.PostEditUserRes;
import com.example.gtc.src.user.repository.dto.request.PostLoginPhoneReq;
import com.example.gtc.src.user.repository.dto.response.PostLoginRes;
import com.example.gtc.src.user.repository.dto.request.PostUserPhoneJoinReq;
import com.example.gtc.src.user.repository.dto.response.GetUserProfileRes;
import com.example.gtc.src.user.repository.dto.response.PostJoinUserRes;
import com.example.gtc.src.user.service.UserServiceImpl;
import com.example.gtc.utils.JwtService;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.gtc.config.BaseResponseStatus.*;
import static com.example.gtc.utils.ValidationRegex.*;

@RestController
@Api(tags ="유저 API ")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserServiceImpl userService;
    private final JwtService jwtService;

    @Autowired  // 의존성 주입을 의한 것
    public UserController(UserServiceImpl userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // 1. create

    /**
     * 회원가입 API (전화번호)
     * [POST]  http://localhost:8080/join-phone
     * @return BaseResponse<?>
     */

    @ApiOperation(value = "회원가입")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK",response = PostJoinUserRes.class),
            @ApiResponse(code = 2012, message = "이름을 입력해주세요."),
            @ApiResponse(code = 2013, message = "사용자 이름을 입력해주세요."),
            @ApiResponse(code = 2014, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 2015, message = "생일을 입력해주세요."),
            @ApiResponse(code = 2021, message = "핸드폰 번호 형식을 확인해주세요."),
            @ApiResponse(code = 2022, message = "비밀번호 형식을 확인해주세요."),
            @ApiResponse(code = 2031, message = "중복된 번호입니다."),
            @ApiResponse(code = 3014, message = "중복된 핸드폰번호입니다."),
            @ApiResponse(code = 3016, message = "중복된 사용자이름입니다."),
            @ApiResponse(code = 3020, message = "약관동의가 필요합니다.")

    })
    @PostMapping("/join-phone")
    public BaseResponse<PostLoginRes> createUser(@RequestBody @NotNull PostUserPhoneJoinReq postUserPhoneReq){
        // validation
        // 1. name 입력 x
        if(postUserPhoneReq.getName().equals("")){
            return new BaseResponse<>(POST_EMPTY_USER_NAME);
        }
        // 2. nickname 입력 x
        if(postUserPhoneReq.getNickname().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }
        // 3. 핸드폰 입력 x
        if(postUserPhoneReq.getPhone().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        // 4. 핸드폰 형식 검사
        if (!isRegexPhoneNumber(postUserPhoneReq.getPhone())) {
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }
        // 5. 비밀번호 입력 x
        if(postUserPhoneReq.getPassword().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        // 6. 비밀번호 형식 검사
        if (!isRegexPassword(postUserPhoneReq.getPassword())) {
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }
        // 7. 생일 입력 x
        if(postUserPhoneReq.getBirth() == 0){
            return new BaseResponse<>(POST_USERS_EMPTY_BIRTH);
        }

       try{
        PostJoinUserRes postUserRes = userService.createUserPhone(postUserPhoneReq);
        return new BaseResponse(postUserRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    // 2. read

    /**
     * 로그인 API  (핸드폰)
     * [POST] http://localhost:8080/logIn-phone
     * @return BaseResponse<PostLoginRes>
     */
    @ApiOperation(value = "로그인")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 1000, message = "OK"),
            @ApiResponse(code = 2013, message = "사용자 이름을 입력해주세요."),
            @ApiResponse(code = 2014, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 3015, message = "없는 아이디거나 비밀번호가 틀렸습니다."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다.")
    })
    @ResponseBody
    @PostMapping("/logIn-phone")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginPhoneReq postLoginReq){
        // 사용자 이름 입력 x
        if(postLoginReq.getPhone().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }
        // 비밀번호 입력 x
        if(postLoginReq.getPassword().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        try{
            PostLoginRes postLoginRes = userService.logInPhone(postLoginReq);

            if(postLoginRes.getAgree().equals("N")){
                // 약관 동의 필요
                return new BaseResponse<>(POST_LOGIN_ERROR_AGREE);
            }else if(postLoginRes.getState() == 2){
                return new BaseResponse<>(POST_LOGIN_ERROR_STATE);
            }

            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 프로필 보기 API
     * [GET] http://localhost:8080/{nickname}
     * @return BaseResponse<GetUserProfileRes>
     */
    @GetMapping("/users/{nickname}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
    })
    @ApiOperation(value = "프로필 보기", notes = "프로필 보기 입니다.")
    public BaseResponse<GetUserProfileRes> retrieveUserProfile(@PathVariable("nickname") String nickname){
        try{
            Long userId = jwtService.getUserIdx();
            System.out.println("userId : "+userId);

            GetUserProfileRes getUserProfileRes = userService.retrieveUserProfile(nickname,userId);
            return new BaseResponse<>(getUserProfileRes);
        } catch(BaseException exception){
           return new BaseResponse<>((exception.getStatus()));
       }
    }

    // 3. update

    /**
     * 비밀번호 변경 API
     * [POST]  http://localhost:8080/users/edit-password
     * @return BaseResponse<PostEditUserRes>
     */
    @ApiOperation(value = "비밀번호 변경")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 1000, message = "OK"),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2014, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 2022, message = "비밀번호 형식을 확인해주세요."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다."),
            @ApiResponse(code = 3030, message = "비밀번호 변경에 실패했습니다. 제대로된 값을 입력해 주세요."),
            @ApiResponse(code = 4011, message = "비밀번호 암호화에 실패하였습니다.")
    })
    @ResponseBody
    @PostMapping("/users/edit-password")
    public BaseResponse<PostEditUserRes> editPassword(@RequestBody Map<String, String> map){
        // 비밀번호 입력 x
        if(map.get("password").equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        // 비밀번호 형식 검사
        if (!isRegexPassword(map.get("password"))) {
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }
        try{
            Long userId = jwtService.getUserIdx();
            PostEditUserRes postEditUserRes = userService.editPassword(map.get("nickname"),map.get("password"),userId);
            return new BaseResponse<>(postEditUserRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 사용자 이름 변경 API
     * [POST]  http://localhost:8080/users/edit-nickname
     * @return BaseResponse<PostEditUserRes>
     */
    @ApiOperation(value = "사용자 이름 변경")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 1000, message = "OK"),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2014, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 3016, message = "중복된 사용자이름입니다."),
            @ApiResponse(code = 3021, message = "정지된 계정입니다."),
            @ApiResponse(code = 3035, message = "사용자 이름 변경에 실패했습니다. 제대로된 값을 입력해 주세요."),
            @ApiResponse(code = 3037, message = "2번이상 변경하셨습니다. 마지막 변경일에서 14일을 뒤에 시도하세요."),
    })
    @ResponseBody
    @PostMapping("/users/edit-nickname")
    public BaseResponse<PostEditUserRes> editNickname(@RequestBody Map<String, String> map){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostEditUserRes postEditUserRes = userService.editNickname(map.get("nickname"), map.get("change-nickname"),userId);
            return new BaseResponse<>(postEditUserRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 4. delete
    /**
     * 계정 삭제 API
     * [DELETE]  http://localhost:8080/users/{nickname}
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 2013, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2014, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 3040, message = "계정삭제에 실패했습니다.")
    })
    @ApiOperation(value = "계정 삭제")
    @DeleteMapping("/users/{nickname}")
    public BaseResponse<DeleteUserRes> deleteUser(@RequestBody Map<String, String> map, @PathVariable("nickname") String nickname){
        // 비밀번호 입력 x
        if(map.get("password").equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            DeleteUserRes userDeleteRes = userService.deleteUser(nickname,map.get("password"),userId);
            return new BaseResponse<>(userDeleteRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
