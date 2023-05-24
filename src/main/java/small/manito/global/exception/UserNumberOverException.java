package small.manito.global.exception;

import small.manito.global.exception.handler.InvalidException;

public class UserNumberOverException extends InvalidException {
    public UserNumberOverException(){
        super("USER NUMBER OVER");
    }
}
