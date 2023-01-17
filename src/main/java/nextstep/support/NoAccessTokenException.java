package nextstep.support;

public class NoAccessTokenException extends RuntimeException{
    public NoAccessTokenException() {
    }

    public NoAccessTokenException(String message) {
        super(message);
    }
}
