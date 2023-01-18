package nextstep.infra.exception.api;

import nextstep.infra.exception.ApiException;
import nextstep.infra.exception.ErrorCode;

public class NoSuchThemeException extends ApiException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_THEME;
    }
}
