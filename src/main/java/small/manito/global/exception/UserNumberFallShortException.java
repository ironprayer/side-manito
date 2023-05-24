package small.manito.global.exception;

import small.manito.global.exception.handler.InvalidException;

public class UserNumberFallShortException extends InvalidException {
    public UserNumberFallShortException(){
        super("USER NUMBER FALL SHORT");
    }
}
