package nextstep.support.exception;

public abstract class ApiException extends RuntimeException{
    public abstract ErrorCode getErrorCode();
}
