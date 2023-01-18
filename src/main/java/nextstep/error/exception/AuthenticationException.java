package nextstep.error.exception;

import nextstep.error.ErrorCode;

public class AuthenticationException extends CustomException {
    private final ErrorCode errorCode;

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode, null);
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
