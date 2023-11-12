package com.example.gtc.domain.user.controller;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.common.config.BaseResponse;
import com.example.gtc.domain.chat.repository.dto.response.PostChatRoomRes;
import com.example.gtc.domain.user.repository.dto.response.DeleteUserRes;
import com.example.gtc.domain.user.repository.dto.response.PostEditUserRes;
import com.example.gtc.domain.user.repository.dto.request.PostLoginPhoneReq;
import com.example.gtc.domain.user.repository.dto.response.PostLoginRes;
import com.example.gtc.domain.user.repository.dto.request.PostUserPhoneJoinReq;
import com.example.gtc.domain.user.repository.dto.response.GetUserProfileRes;
import com.example.gtc.domain.user.repository.dto.response.PostJoinUserRes;
import com.example.gtc.domain.user.service.ConfirmService;
import com.example.gtc.domain.user.service.UserServiceImpl;
import com.example.gtc.common.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.gtc.common.config.BaseResponseStatus.*;
import static com.example.gtc.common.utils.ValidationRegex.*;

@Tag(name ="유저 API ")
@Setter
@RestController
@RequiredArgsConstructor
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final ConfirmService confirmService;

    // 1. create

    /**
     * 회원가입 API (전화번호)
     * [POST]  http://localhost:8080/join-phone
     * @return BaseResponse<?>
     */

    @Operation(summary = "회원가입")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = PostJoinUserRes.class))),
            @ApiResponse(responseCode = "2012", description = "이름을 입력해주세요."),
            @ApiResponse(responseCode = "2013", description = "사용자 이름을 입력해주세요."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "2015", description = "생일을 입력해주세요."),
            @ApiResponse(responseCode = "2021", description = "핸드폰 번호 형식을 확인해주세요."),
            @ApiResponse(responseCode = "2022", description = "비밀번호 형식을 확인해주세요."),
            @ApiResponse(responseCode = "2031", description = "중복된 번호입니다."),
            @ApiResponse(responseCode = "3014", description = "중복된 핸드폰번호입니다."),
            @ApiResponse(responseCode = "3016", description = "중복된 사용자이름입니다."),
            @ApiResponse(responseCode = "3020", description = "약관동의가 필요합니다.")

    })
    @PostMapping("/join-phone")
    public BaseResponse<PostLoginRes> createUser(@RequestBody @NotNull PostUserPhoneJoinReq postUserPhoneReq) throws CoolsmsException {
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
        // 8. 유저 타입 정보 x
        if(postUserPhoneReq.getUserType() != 0 && postUserPhoneReq.getUserType() != 1){
            return new BaseResponse<>(POST_USERS_INVALID_USER_TYPE);
        }

       try{
           PostJoinUserRes postUserRes = userService.createUserPhone(postUserPhoneReq);
           return new BaseResponse(postUserRes);

        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }


    static String checkPhone;
    /**
     * 휴대폰 인증 API  (핸드폰)
     * [POST] http://localhost:8080/phone-confirm
     * @return BaseResponse<PostLoginRes>
     */
    @Operation(summary = "휴대폰 인증")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "사용자 이름을 입력해주세요.")
    })
    @ResponseBody
    @PostMapping("/phone-confirm")
    public boolean phoneConfirm(@RequestBody Map<String, String> map) throws CoolsmsException {
        if(map.get("phone-confirm").equals(this.checkPhone)){
            // userService.updatePhoneConfirm();
            System.out.println("인증이 완료됨");
            this.checkPhone="";
            return true;
        }
        return false;
    }

    // http://localhost:8080/phone-confirm?phone=
    @Operation(summary = "휴대폰 인증")
    @GetMapping("/phone-confirm")
    public String phoneConfirm(@RequestParam("phone") String phone) throws CoolsmsException {
        this.checkPhone = confirmService.phoneConfirm(phone);
        return this.checkPhone;
    }

     // 2. read
    /**
     * 로그인 API  (핸드폰)
     * [POST] http://localhost:8080/logIn-phone
     * @return BaseResponse<PostLoginRes>
     */
    @Operation(summary = "로그인")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "사용자 이름을 입력해주세요."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "3015", description = "없는 아이디거나 비밀번호가 틀렸습니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
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
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
    })
    @Operation(summary = "프로필 보기", description = "프로필 보기 입니다.")
    public ResponseEntity<GetUserProfileRes> retrieveUserProfile(@PathVariable("nickname") String nickname) throws Exception {
        Long userId = jwtService.getUserIdx();
        GetUserProfileRes getUserProfileRes = userService.retrieveUserProfile(userId, nickname);
        return ResponseEntity.ok(getUserProfileRes);

//        try{
//            Long userId = jwtService.getUserIdx();
//            System.out.println("userId : "+userId);
//
//            GetUserProfileRes getUserProfileRes = userService.retrieveUserProfile(nickname,userId);
//            return new BaseResponse<>(getUserProfileRes);
//        } catch(BaseException exception){
//           return new BaseResponse<>((exception.getStatus()));
//       }
    }

    // 3. update

    /**
     * 비밀번호 변경 API
     * [POST]  http://localhost:8080/users/edit-password
     * @return BaseResponse<PostEditUserRes>
     */
    @Operation(summary = "비밀번호 변경")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "2022", description = "비밀번호 형식을 확인해주세요."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다."),
            @ApiResponse(responseCode = "3030", description = "비밀번호 변경에 실패했습니다. 제대로된 값을 입력해 주세요."),
            @ApiResponse(responseCode = "4011", description = "비밀번호 암호화에 실패하였습니다.")
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
    @Operation(summary = "사용자 이름 변경")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "3016", description = "중복된 사용자이름입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다."),
            @ApiResponse(responseCode = "3035", description = "사용자 이름 변경에 실패했습니다. 제대로된 값을 입력해 주세요."),
            @ApiResponse(responseCode = "3037", description = "2번이상 변경하셨습니다. 마지막 변경일에서 14일을 뒤에 시도하세요."),
    })
    @ResponseBody
    @PostMapping("/users/edit-nickname")
    public BaseResponse<PostEditUserRes> editNickname(@RequestBody Map<String, String> map){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostEditUserRes postEditUserRes = userService.editNickname(userId, map.get("change-nickname"));
            return new BaseResponse<>(postEditUserRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 프로필 사진 변경 API
     * [POST]  http://localhost:8080/users/edit-user-img
     * @return BaseResponse<PostEditUserRes>
     */
    @Operation(summary = "프로필 사진 변경")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "3016", description = "중복된 사용자이름입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
    })
    @ResponseBody
    @PostMapping("/users/edit-user-img")
    public BaseResponse<PostEditUserRes> editUserImg(@RequestBody Map<String, String> map){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostEditUserRes postEditUserRes = userService.editUserImg(userId, map.get("change-user-img"));
            return new BaseResponse<>(postEditUserRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 웹사이트 변경 API
     * [POST]  http://localhost:8080/users/edit-website
     * @return BaseResponse<PostEditUserRes>
     */
    @Operation(summary = "웹사이트 변경")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "3016", description = "중복된 사용자이름입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
    })
    @ResponseBody
    @PostMapping("/users/edit-website")
    public BaseResponse<PostEditUserRes> editWebsite(@RequestBody Map<String, String> map){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostEditUserRes postEditUserRes = userService.editWebsite(userId, map.get("change-website"));
            return new BaseResponse<>(postEditUserRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 소개 변경 API
     * [POST]  http://localhost:8080/users/edit-bio
     * @return BaseResponse<PostEditUserRes>
     */
    @Operation(summary = "소개 변경")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(responseCode = "1000", description = "OK"),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "3016", description = "중복된 사용자이름입니다."),
            @ApiResponse(responseCode = "3021", description = "정지된 계정입니다.")
    })
    @ResponseBody
    @PostMapping("/users/edit-bio")
    public BaseResponse<PostEditUserRes> editBio(@RequestBody Map<String, String> map){
        try{
            // jwt
            Long userId = jwtService.getUserIdx();
            PostEditUserRes postEditUserRes = userService.editBio(userId, map.get("change-bio"));
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
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "2013", description = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(responseCode = "2014", description = "비밀번호를 입력해주세요."),
            @ApiResponse(responseCode = "3040", description = "계정삭제에 실패했습니다.")
    })
    @Operation(summary = "계정 삭제")
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
