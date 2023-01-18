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

    public RoomEscapeException(String message) {
        super(message);
    }

    public RoomEscapeException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public RoomEscapeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoomEscapeException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public RoomEscapeException(Throwable cause) {
        super(cause);
    }

    public RoomEscapeException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

