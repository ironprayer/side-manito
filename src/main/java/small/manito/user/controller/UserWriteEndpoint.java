package small.manito.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import small.manito.auth.AuthPayload;
import small.manito.auth.controller.request.LoginRequest;
import small.manito.auth.controller.response.TokenResponse;
import small.manito.auth.service.AuthService;
import small.manito.global.exception.InvalidUserException;
import small.manito.user.controller.request.PredictRequest;
import small.manito.user.controller.response.PredictResponse;
import small.manito.user.service.UserWriteService;

@RestController
@RequiredArgsConstructor
public class UserWriteEndpoint {
    private final UserWriteService userWriteService;
    private final AuthService authService;

    @PostMapping("users")
    void createUser(
            @RequestBody LoginRequest loginRequest
    ){
        if(!authService.hasTextUser(loginRequest.getId(), loginRequest.getPassword())) throw new InvalidUserException();

        authService.create(loginRequest.getId(), loginRequest.getPassword());

        // 아이디 중복만 체크해서 알려주면 되겠다
    };

    @PostMapping("users/login")
    TokenResponse login(@RequestBody LoginRequest loginRequest){
        if(!authService.hasTextUser(loginRequest.getId(), loginRequest.getPassword())) throw new InvalidUserException();

        return authService.login(loginRequest.getId(), loginRequest.getPassword());
    };

    @PostMapping("users/predict")
    PredictResponse predictManito(
            @RequestBody PredictRequest predictRequest,
            @AuthenticationPrincipal AuthPayload authPayload
    ){
        return PredictResponse.from(userWriteService.getManitoResult(predictRequest.getGroupId()
                , authPayload.getUserId()
                , predictRequest.getManiteeName()));
    }
}
