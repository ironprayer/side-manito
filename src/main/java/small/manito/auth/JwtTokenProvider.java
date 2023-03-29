package small.manito.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import small.manito.controller.response.TokenResponse;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

public class JwtTokenProvider {

    private static final Key SIGNING_KEY = getSigningKey();
    private static final String secretKey = "manitoSecretKeyqazwsxedcddedwdwedewdwedwedeededed";
    private static final int ACCESS_TOKEN_DURATION_SECONDS = 60 * 30;
    private static final int REFRESH_TOKEN_DURATION_SECONDS = 60 * 60 * 24 * 7;


    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public static TokenResponse generateToken(Long userId) {
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
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setClaims(Map.of(
                        "userId", userId,
                        "type", "REFRESH",
                        "iat", now.getEpochSecond(),
                        "exp", expiryDateOfRefreshToken.getEpochSecond()
                ))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessExpiredDate(expiryDateOfAccessToken.atZone(ZoneId.systemDefault()).toLocalDateTime())
                .refreshToken(refreshToken)
                .refreshExpiredDate(expiryDateOfRefreshToken.atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    public static String parseTokenType(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type", String.class);
    }

    public static Long parseUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    // 토큰 정보를 검증하는 메서드
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token);
            System.out.println(SIGNING_KEY);
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

    public static Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}