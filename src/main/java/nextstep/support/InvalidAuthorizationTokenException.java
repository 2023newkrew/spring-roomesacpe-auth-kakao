package nextstep.support;

public class InvalidAuthorizationTokenException extends RuntimeException {
    public InvalidAuthorizationTokenException() {
        super();
    }

    public InvalidAuthorizationTokenException(String message) {
        super(message);
    }


    public InvalidAuthorizationTokenException(String message, Throwable cause) {
        super(message, cause);
    }


    public InvalidAuthorizationTokenException(Throwable cause) {
        super(cause);
    }
}
