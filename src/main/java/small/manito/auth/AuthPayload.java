package small.manito.auth;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;

@Getter
@Hidden
public class AuthPayload {
    private Long userId;

    public AuthPayload(Long userId){
        this.userId = userId;
    }
}
