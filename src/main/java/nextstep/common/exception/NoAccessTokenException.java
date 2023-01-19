package nextstep.common.exception;

public class NoAccessTokenException extends RuntimeException {
    public NoAccessTokenException() {
    }

    public NoAccessTokenException(String message) {
        super(message);
    }
}
