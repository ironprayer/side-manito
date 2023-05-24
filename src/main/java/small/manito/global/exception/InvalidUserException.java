package small.manito.global.exception;

import small.manito.global.exception.handler.InvalidException;

public class InvalidUserException extends InvalidException {
    public InvalidUserException(){super("invalid User Exception");}
}
