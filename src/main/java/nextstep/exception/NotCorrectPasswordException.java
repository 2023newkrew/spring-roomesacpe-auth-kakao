package nextstep.exception;

import nextstep.error.ErrorCode;

public class NotCorrectPasswordException extends RoomEscapeException {

    private ErrorCode errorCode;

    public NotCorrectPasswordException() {
        super();
    }

    public NotCorrectPasswordException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public NotCorrectPasswordException(String message) {
        super(message);
    }

    public NotCorrectPasswordException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotCorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCorrectPasswordException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NotCorrectPasswordException(Throwable cause) {
        super(cause);
    }

    public NotCorrectPasswordException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
