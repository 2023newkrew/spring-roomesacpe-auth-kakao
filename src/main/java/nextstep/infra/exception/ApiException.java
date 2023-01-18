package nextstep.infra.exception;

public abstract class ApiException extends RuntimeException{
    public abstract ErrorCode getErrorCode();
}
