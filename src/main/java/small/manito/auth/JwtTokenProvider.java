package small.manito.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import small.manito.controller.response.TokenResponse;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final Key key;
    private static String secretKey = "manitoSecretKeyqazwsxedcddedwdwedewdwedwedeededed";
    private static int ACCESS_TOKEN_DURATION_SECONDS = 60 * 30;
    private static int REFRESH_TOKEN_DURATION_SECONDS = 60 * 60 * 24 * 7;

    public JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenResponse generateToken(Long userId) {
        var now = Instant.now();
        var expiryDateOfAccessToken = now.plusSeconds(ACCESS_TOKEN_DURATION_SECONDS);
        var expiryDateOfRefreshToken = now.plusSeconds(REFRESH_TOKEN_DURATION_SECONDS);

        String accessToken = Jwts.builder()
                .setClaims(Map.of(
                        "userId", userId,
                        "type", "ACCESS",
                        "iat", now.getEpochSecond(),
                        "exp", expiryDateOfAccessToken.getEpochSecond()
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setClaims(Map.of(
                        "userId", userId,
                        "type", "REFRESH",
                        "iat", now.getEpochSecond(),
                        "exp", expiryDateOfRefreshToken.getEpochSecond()
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessExpiredDate(expiryDateOfAccessToken.atZone(ZoneId.systemDefault()).toLocalDateTime())
                .refreshToken(refreshToken)
                .refreshExpiredDate(expiryDateOfRefreshToken.atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    public String parseTokenType(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type", String.class);
    }

    public Long parseUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}