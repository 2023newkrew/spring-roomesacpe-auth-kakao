package nextstep.support.exception;

import nextstep.support.ErrorCode;

public class NotReservationOwnerException extends RoomEscapeException{
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_RESERVATION_OWNER;
    }
}
