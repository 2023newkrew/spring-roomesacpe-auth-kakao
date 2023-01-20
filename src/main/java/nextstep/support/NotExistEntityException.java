package nextstep.support;

public class NotExistEntityException extends RuntimeException {
    public NotExistEntityException() {
        super();
    }

    public NotExistEntityException(String message) {
        super(message);
    }
}
