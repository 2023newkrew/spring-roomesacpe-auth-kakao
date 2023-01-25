package nextstep.support.exception;

public class NotExistThemeException extends RuntimeException {
    public NotExistThemeException() {
    }

    public NotExistThemeException(String message) {
        super(message);
    }
}
