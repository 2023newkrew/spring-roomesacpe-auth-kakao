package nextstep.exception;

import nextstep.error.ErrorCode;

public class RoomEscapeException extends RuntimeException {

    private ErrorCode errorCode;

    public RoomEscapeException() {
        super();
    }

    public RoomEscapeException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public RoomEscapeException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public RoomEscapeException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public RoomEscapeException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

