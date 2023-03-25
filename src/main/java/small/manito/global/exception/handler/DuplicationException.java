package small.manito.global.exception.handler;

public abstract class DuplicationException extends RuntimeException {

    public DuplicationException() {
    }

    public DuplicationException(String msg) {
        super(msg);
    }

}
