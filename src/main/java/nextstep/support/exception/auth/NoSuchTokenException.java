package nextstep.support.exception.auth;

import nextstep.support.ErrorCode;
import nextstep.support.exception.RoomEscapeException;

public class NoSuchTokenException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_TOKEN;
    }
}
