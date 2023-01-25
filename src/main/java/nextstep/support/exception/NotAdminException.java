package nextstep.support.exception;

public class NotAdminException extends RuntimeException {

    public NotAdminException() {
    }

    public NotAdminException(String message) {
        super(message);
    }
}
