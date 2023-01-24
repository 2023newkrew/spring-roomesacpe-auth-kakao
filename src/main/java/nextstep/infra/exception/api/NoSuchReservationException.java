package nextstep.infra.exception.api;

import nextstep.infra.exception.ErrorCode;
import nextstep.infra.exception.ApiException;

public class NoSuchReservationException extends ApiException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_RESERVATION;
    }
}
