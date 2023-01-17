package nextstep.support.excpetion;

public class NotExistEntityException extends RuntimeException {
    public NotExistEntityException() {
        super("존재하지 않는 엔티티");
    }

    public NotExistEntityException(String message) {
        super(message);
    }


    public NotExistEntityException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotExistEntityException(Throwable cause) {
        super(cause);
    }
}
