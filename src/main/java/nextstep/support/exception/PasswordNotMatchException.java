package nextstep.support.exception;

import nextstep.support.ErrorCode;

public class PasswordNotMatchException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PASSWORD_NOT_MATCH;
    }
}
