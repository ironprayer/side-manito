package small.manito.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema (description = "로그인 정보")
@Getter
public class LoginRequest {
    @Schema(description = "유형", defaultValue = "0", allowableValues = {"0", "1", "2"})
    private String id;
    private String password;
}
