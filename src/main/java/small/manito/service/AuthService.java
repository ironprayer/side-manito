package small.manito.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import small.manito.auth.JwtTokenProvider;
import small.manito.controller.response.TokenResponse;
import small.manito.global.exception.UnAuthorizedException;
import small.manito.global.exception.UserDuplicationException;
import small.manito.global.exception.handler.DuplicationException;
import small.manito.querydsl.entity.User;
import small.manito.repository.UserRepository;

@Getter
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public void create(String userId, String password){
        if( !userRepository.findByUserId(userId).isEmpty() ){
            throw new UserDuplicationException(userId);
        }

        // SHA256 적용해보자
        userRepository.save(
                User.builder()
                        .userId(userId)
                        .password(password)
                        .build()
        );
    }

    public TokenResponse login(String userId, String password) {
        var user = userRepository.findByUserId(userId).get();

        if(!user.isMatchedPassword(password)){
            throw new UnAuthorizedException();
        }

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponse tokenInfo = jwtTokenProvider.generateToken(user.getId());

        return tokenInfo;
    }
}
