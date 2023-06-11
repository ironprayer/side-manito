package small.manito.auth.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import small.manito.auth.JwtTokenProvider;
import small.manito.auth.controller.response.TokenResponse;
import small.manito.global.exception.UnAuthorizedException;
import small.manito.user.repository.UserRepository;

@Getter
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TokenResponse login(String userId, String password) {
        var user = userRepository.findByUserId(userId).get();

        if(!user.isMatchedPassword(password)){
            throw new UnAuthorizedException();
        }

        TokenResponse tokenInfo = JwtTokenProvider.generateToken(user.getId());

        return tokenInfo;
    }

    public boolean hasTextUser(String userId, String password){
        return StringUtils.hasText(userId) && StringUtils.hasText(password);
    }
}
