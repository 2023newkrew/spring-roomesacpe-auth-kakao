package nextstep.exception;

import nextstep.error.ErrorCode;

public class NotCorrectPasswordException extends RoomEscapeException {

    private ErrorCode errorCode;

    public NotCorrectPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotCorrectPasswordException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotCorrectPasswordException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public NotCorrectPasswordException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
