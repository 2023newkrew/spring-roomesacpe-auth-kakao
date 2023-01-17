package nextstep.support.exception.api;

import nextstep.support.ErrorCode;
import nextstep.support.exception.RoomEscapeException;

public class NoSuchReservationException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_RESERVATION;
    }
}
