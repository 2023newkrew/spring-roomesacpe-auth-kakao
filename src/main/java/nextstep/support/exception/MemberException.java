package nextstep.support.exception;

public class MemberException extends RoomEscapeException {

    public MemberException(RoomEscapeExceptionCode roomEscapeExceptionCode) {
        super(roomEscapeExceptionCode);
    }
}
