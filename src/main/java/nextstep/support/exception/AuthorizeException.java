package nextstep.support.exception;

public abstract class AuthorizeException extends RuntimeException {
    public abstract ErrorCode getErrorCode();
}
