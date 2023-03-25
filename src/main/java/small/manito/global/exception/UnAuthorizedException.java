package small.manito.global.exception;

import small.manito.global.exception.handler.AuthException;

public class UnAuthorizedException extends AuthException {
    public UnAuthorizedException(){super("UnAuth");}
}
