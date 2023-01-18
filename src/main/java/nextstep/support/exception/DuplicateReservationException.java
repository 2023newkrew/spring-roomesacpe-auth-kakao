package nextstep.support.exception;

import nextstep.support.ErrorCode;

public class DuplicateReservationException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_RESERVATION;
    }
}
