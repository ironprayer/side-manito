package small.manito.global.exception.handler;

public abstract class InvalidException extends RuntimeException {
    public InvalidException() {}

    public InvalidException(String msg) {
        super(msg);
    }
}
