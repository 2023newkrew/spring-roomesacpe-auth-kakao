package nextstep.support.exception;

import nextstep.support.ErrorCode;

public class AuthorizationException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_AUTHORIZED;
    }
}
