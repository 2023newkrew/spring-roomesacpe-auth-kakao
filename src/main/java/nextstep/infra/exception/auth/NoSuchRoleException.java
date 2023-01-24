package nextstep.infra.exception.auth;

import nextstep.infra.exception.ErrorCode;
import nextstep.infra.exception.AuthorizeException;

public class NoSuchRoleException extends AuthorizeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_ROLE;
    }
}
