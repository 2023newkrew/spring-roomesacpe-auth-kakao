package nextstep.support;

public class PasswordNotMatchException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PASSWORD_NOT_MATCH;
    }
}
