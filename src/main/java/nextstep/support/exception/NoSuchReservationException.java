package nextstep.support.exception;

import nextstep.support.ErrorCode;

public class NoSuchReservationException extends RoomEscapeException{
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_RESERVATION;
    }
}
