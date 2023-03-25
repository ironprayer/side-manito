package small.manito.global.exception;


import small.manito.global.exception.handler.DuplicationException;

public class UserDuplicationException extends DuplicationException {

    public UserDuplicationException(String userId) {
        super(String.format("User is Duplication - userId: %s", userId));
    }
}
