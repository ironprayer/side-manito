package small.manito.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import small.manito.auth.AuthPayload;
import small.manito.auth.JwtTokenProvider;
import small.manito.auth.controller.response.TokenResponse;

@RestController
public class AuthController {

    @PostMapping("auth/refresh")
    TokenResponse refreshToken(@AuthenticationPrincipal AuthPayload authPayload){
        return JwtTokenProvider.generateToken(authPayload.getUserId());
    }
}
