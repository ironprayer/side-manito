package small.manito.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@ToString
@Getter
public class TokenResponse {
    private String accessToken;
    private LocalDateTime accessExpiredDate;
    private String refreshToken;
    private LocalDateTime refreshExpiredDate;
}
