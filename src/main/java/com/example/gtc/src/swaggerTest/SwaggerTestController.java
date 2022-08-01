package com.example.gtc.src.swaggerTest;

//import com.story.example.lib.Response;
//import com.story.example.main.dto.RegisterRequest;
//import com.story.example.main.dto.RegisterResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RestController
@Api(tags ="SwaggerTest API 입니다.")
public class SwaggerTestController {

    @ApiOperation(value = "스웨거 테스트")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Exception")
    })
    @GetMapping("/api")
    public String sayHello() {
        return "Swagger Hello World!";
    }

//    private final Response response;
//
//    public MainController(Response response) {
//        this.response = response;
//    }
//
//    @ApiOperation(value = "회원 정보", notes = "회원에 대한 정보 출력")
//    @GetMapping("/member/{idx}")
//    public ResponseEntity<?> getMember(@PathVariable String idx) {
//        return response.success();
//    }
//
//    @ApiOperation(value = "회원 등록", notes = "신규 회원 등록", response = RegisterResponse.class)
//    @PostMapping("/member")
//    public ResponseEntity<?> registerMember(RegisterRequest registerRequest) {
//        return response.success();
//    }
}
