package nextstep.support.exception;

import nextstep.support.ErrorCode;

public abstract class RoomEscapeException extends RuntimeException{
    public abstract ErrorCode getErrorCode();
}
