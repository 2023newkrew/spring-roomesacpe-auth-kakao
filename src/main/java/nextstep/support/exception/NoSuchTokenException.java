package nextstep.support.exception;

import nextstep.support.ErrorCode;

public class NoSuchTokenException extends RoomEscapeException{
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_TOKEN;
    }
}
