package nextstep.support.excpetion;

public class InvalidAuthorizationTokenException extends RuntimeException {
    public InvalidAuthorizationTokenException() {
        super("올바르지 않은 형식의 인증 토큰");
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
