package small.manito.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserAuthentication extends AbstractAuthenticationToken {

    private AuthPayload authPayload;

    public UserAuthentication(Long userId) {
        super(null);
        this.authPayload = new AuthPayload(userId);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public AuthPayload getPrincipal() {
        return authPayload;
    }
}
