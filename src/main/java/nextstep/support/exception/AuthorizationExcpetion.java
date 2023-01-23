package nextstep.support.exception;

public class AuthorizationExcpetion extends RoomEscapeException {
    public AuthorizationExcpetion(RoomEscapeExceptionCode roomEscapeExceptionCode) {
        super(roomEscapeExceptionCode);
    }
}
