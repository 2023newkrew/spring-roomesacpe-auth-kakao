package nextstep.infra.exception;

public abstract class AuthorizeException extends RuntimeException {
    public abstract ErrorCode getErrorCode();
}
