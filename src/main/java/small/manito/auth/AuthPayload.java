package small.manito.auth;

import lombok.Getter;

@Getter
public class AuthPayload {
    private Long userId;

    public AuthPayload(Long userId){
        this.userId = userId;
    }
}
