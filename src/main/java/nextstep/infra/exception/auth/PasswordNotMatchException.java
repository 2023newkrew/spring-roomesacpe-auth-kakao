package nextstep.infra.exception.auth;

import nextstep.infra.exception.ErrorCode;
import nextstep.infra.exception.AuthorizeException;

public class PasswordNotMatchException extends AuthorizeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PASSWORD_NOT_MATCH;
    }
}
