package nextstep.infra.exception.auth;

import nextstep.infra.exception.ErrorCode;
import nextstep.infra.exception.AuthorizeException;

public class AuthorizationException extends AuthorizeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_AUTHORIZED;
    }
}
