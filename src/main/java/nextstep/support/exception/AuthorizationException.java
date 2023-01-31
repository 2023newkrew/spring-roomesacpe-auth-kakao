package nextstep.support.exception;

public class AuthorizationException extends RoomEscapeException {
    public AuthorizationException(RoomEscapeExceptionCode roomEscapeExceptionCode) {
        super(roomEscapeExceptionCode);
    }
}
