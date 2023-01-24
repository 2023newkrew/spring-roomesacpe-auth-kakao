package nextstep.infra.exception.auth;

import nextstep.infra.exception.ErrorCode;
import nextstep.infra.exception.AuthorizeException;

public class NoSuchTokenException extends AuthorizeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_TOKEN;
    }
}
