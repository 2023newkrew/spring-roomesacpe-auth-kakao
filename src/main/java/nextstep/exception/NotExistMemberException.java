package nextstep.exception;

public class NotExistMemberException extends NotExistEntityException {
    public NotExistMemberException() {
        super();
    }

    public NotExistMemberException(String message) {
        super(message);
    }


    public NotExistMemberException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotExistMemberException(Throwable cause) {
        super(cause);
    }
}
