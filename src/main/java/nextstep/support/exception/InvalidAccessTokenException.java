package nextstep.support.exception;

public class InvalidAccessTokenException extends RuntimeException {
    public InvalidAccessTokenException() {
    }

    public InvalidAccessTokenException(String message) {
        super(message);
    }
}
