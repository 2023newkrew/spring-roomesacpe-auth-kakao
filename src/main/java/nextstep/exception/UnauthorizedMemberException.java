package nextstep.exception;

import nextstep.error.ErrorCode;

public class UnauthorizedMemberException extends RoomEscapeException {

    private ErrorCode errorCode;

    public UnauthorizedMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnauthorizedMemberException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UnauthorizedMemberException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public UnauthorizedMemberException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
