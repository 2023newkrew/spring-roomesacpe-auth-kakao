package nextstep.support.excpetion;

public class NotCorrectPasswordException extends RuntimeException {
    public NotCorrectPasswordException() {
        super("패스워드가 일치하지 않습니다.");
    }

    public NotCorrectPasswordException(String message) {
        super(message);
    }


    public NotCorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotCorrectPasswordException(Throwable cause) {
        super(cause);
    }
}
