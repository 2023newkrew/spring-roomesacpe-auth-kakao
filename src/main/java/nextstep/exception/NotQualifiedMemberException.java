package nextstep.exception;

import nextstep.error.ErrorCode;

public class NotQualifiedMemberException extends RoomEscapeException {

    private ErrorCode errorCode;

    public NotQualifiedMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotQualifiedMemberException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotQualifiedMemberException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public NotQualifiedMemberException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
