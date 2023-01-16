package nextstep.support;

public class AuthorizationException extends RoomEscapeException{
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_AUTHORIZED;
    }
}
