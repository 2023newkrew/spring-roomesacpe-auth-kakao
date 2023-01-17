package nextstep.support.exception.auth;

import nextstep.support.ErrorCode;
import nextstep.support.exception.RoomEscapeException;

public class AuthorizationException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_AUTHORIZED;
    }
}
