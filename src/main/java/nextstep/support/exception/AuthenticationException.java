package nextstep.support.exception;

public class AuthenticationException extends RoomEscapeException {
    public AuthenticationException(RoomEscapeExceptionCode roomEscapeExceptionCode) {
        super(roomEscapeExceptionCode);
    }
}
