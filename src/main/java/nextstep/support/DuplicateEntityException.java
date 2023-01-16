package nextstep.support;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(String message) {
        super(message);
    }


    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }


    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }
}
