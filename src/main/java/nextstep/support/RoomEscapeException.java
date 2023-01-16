package nextstep.support;

public abstract class RoomEscapeException extends RuntimeException{
    public abstract ErrorCode getErrorCode();
}
