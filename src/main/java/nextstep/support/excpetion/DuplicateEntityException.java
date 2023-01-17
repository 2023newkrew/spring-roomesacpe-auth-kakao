package nextstep.support.excpetion;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
        super("중복된 엔티티");
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
