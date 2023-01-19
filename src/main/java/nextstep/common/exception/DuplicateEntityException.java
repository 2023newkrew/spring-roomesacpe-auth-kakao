package nextstep.common.exception;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
