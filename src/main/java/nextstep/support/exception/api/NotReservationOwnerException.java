package nextstep.support.exception.api;

import nextstep.support.ErrorCode;
import nextstep.support.exception.RoomEscapeException;

public class NotReservationOwnerException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_RESERVATION_OWNER;
    }
}
