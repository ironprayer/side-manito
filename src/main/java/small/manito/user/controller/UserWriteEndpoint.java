package small.manito.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import small.manito.auth.AuthPayload;
import small.manito.auth.controller.request.LoginRequest;
import small.manito.auth.controller.response.TokenResponse;
import small.manito.auth.service.AuthService;
import small.manito.group.service.ManitoGroupQueryService;
import small.manito.group.service.ManitoGroupWriteService;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.user.controller.request.PredictRequest;
import small.manito.user.controller.response.PredictResponse;

@RestController
@RequiredArgsConstructor
public class UserWriteEndpoint {
    private final ManitoGroupWriteService manitoGroupWriteService;
    private final AuthService authService;

    // 회원가입
    @PostMapping("users")
    void createUser(
            @RequestBody LoginRequest loginRequest
    ){
        authService.create(loginRequest.getId(), loginRequest.getPassword());

        // 아이디 중복만 체크해서 알려주면 되겠다
    };

    // 로그인
    @PostMapping("users/login")
    TokenResponse login(@RequestBody LoginRequest loginRequest){

        var token = authService.login(loginRequest.getId(), loginRequest.getPassword());
        System.out.println(token);
        return token;
    };

    @PostMapping("users/predict")
    PredictResponse predictManito(
            @RequestBody PredictRequest predictRequest,
            @AuthenticationPrincipal AuthPayload authPayload
    ){
        return PredictResponse.from(manitoGroupWriteService.getManitoResult(predictRequest.getGroupId()
                , authPayload.getUserId()
                , predictRequest.getManiteeName()));
    }

    // 회원탈퇴
    @DeleteMapping("users")
    void deleteUser(){};


    // 채팅 입력
    @PostMapping("users/chat")
    void inputChat(){};
}
