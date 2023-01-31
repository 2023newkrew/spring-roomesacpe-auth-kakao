package nextstep.exception;

public class NotExistEntityException extends RuntimeException {
    public NotExistEntityException() {
    }

    public NotExistEntityException(String message) {
        super(message);
    }
}
