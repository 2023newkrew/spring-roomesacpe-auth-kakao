package nextstep.infra.exception.auth;

import nextstep.infra.exception.AuthorizeException;
import nextstep.infra.exception.ErrorCode;

public class NoSuchMemberException extends AuthorizeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_MEMBER;
    }
}
