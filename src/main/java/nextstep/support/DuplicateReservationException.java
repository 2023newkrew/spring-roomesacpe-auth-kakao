package nextstep.support;

public class DuplicateReservationException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_RESERVATION;
    }
}
