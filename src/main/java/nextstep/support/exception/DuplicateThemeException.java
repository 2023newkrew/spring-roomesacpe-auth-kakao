package nextstep.support.exception;

public class DuplicateThemeException extends RuntimeException {
    public DuplicateThemeException() {
    }

    public DuplicateThemeException(String message) {
        super(message);
    }
}
