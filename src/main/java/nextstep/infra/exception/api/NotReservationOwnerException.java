package nextstep.infra.exception.api;

import nextstep.infra.exception.ApiException;
import nextstep.infra.exception.ErrorCode;

public class NotReservationOwnerException extends ApiException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_RESERVATION_OWNER;
    }
}
