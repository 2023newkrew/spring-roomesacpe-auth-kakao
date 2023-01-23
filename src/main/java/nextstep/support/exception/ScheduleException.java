package nextstep.support.exception;

public class ScheduleException extends RoomEscapeException {
    public ScheduleException(RoomEscapeExceptionCode roomEscapeExceptionCode) {
        super(roomEscapeExceptionCode);
    }
}
