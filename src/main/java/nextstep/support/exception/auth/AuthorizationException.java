package nextstep.support.exception.auth;

import nextstep.support.exception.AuthorizeException;
import nextstep.support.exception.ErrorCode;
import nextstep.support.exception.ApiException;

public class AuthorizationException extends AuthorizeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_AUTHORIZED;
    }
}
