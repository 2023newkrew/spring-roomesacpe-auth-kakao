package nextstep.exception;

public class NotQualifiedMemberException extends RuntimeException {
    public NotQualifiedMemberException() {
        super();
    }

    public NotQualifiedMemberException(String message) {
        super(message);
    }


    public NotQualifiedMemberException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotQualifiedMemberException(Throwable cause) {
        super(cause);
    }
}
