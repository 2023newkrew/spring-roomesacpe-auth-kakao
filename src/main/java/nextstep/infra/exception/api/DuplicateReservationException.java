package nextstep.infra.exception.api;

import nextstep.infra.exception.ApiException;
import nextstep.infra.exception.ErrorCode;

public class DuplicateReservationException extends ApiException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_RESERVATION;
    }
}
