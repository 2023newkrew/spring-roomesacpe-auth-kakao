package nextstep.support.exception.api;

import nextstep.support.ErrorCode;
import nextstep.support.exception.RoomEscapeException;

public class DuplicateReservationException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_RESERVATION;
    }
}
