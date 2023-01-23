package nextstep.support.exception;

public class ReservationException extends RoomEscapeException {

    public ReservationException(RoomEscapeExceptionCode roomEscapeExceptionCode) {
        super(roomEscapeExceptionCode);
    }
}
