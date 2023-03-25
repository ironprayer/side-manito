package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import small.manito.controller.request.LoginRequest;
import small.manito.controller.request.UserRequest;
import small.manito.controller.response.TokenResponse;
import small.manito.service.AuthService;
import small.manito.service.ManitoGroupService;

@RestController
@RequiredArgsConstructor
public class UserWriteEndpoint {
    private final ManitoGroupService manitoGroupService;
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

    /*
    - 마니또 그룹 아이디가 있고 그룹 아이디를 통해 참여자 등록을 할 수 있다.
    - 참여자 등록 시에는 실제 이름과 닉네임을 필수로 받는다.

     */
    @PostMapping("users/join")
    void joinGroup(
            @RequestBody UserRequest userRequest){
        manitoGroupService.join(userRequest.getGroupId(), userRequest.toUser());
    }

    // 회원탈퇴
    @DeleteMapping("users")
    void deleteUser(){};


    // 채팅 입력
    @PostMapping("users/chat")
    void inputChat(){};
}
