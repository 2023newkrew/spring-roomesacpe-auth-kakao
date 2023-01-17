package nextstep.support.exception.api;

import nextstep.support.exception.ErrorCode;
import nextstep.support.exception.ApiException;

public class DuplicateReservationException extends ApiException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_RESERVATION;
    }
}
