package nextstep.exception;

import nextstep.error.ErrorCode;

public class InvalidAuthorizationTokenException extends RoomEscapeException {

    private ErrorCode errorCode;

    public InvalidAuthorizationTokenException() {
        super();
    }

    public InvalidAuthorizationTokenException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public InvalidAuthorizationTokenException(String message) {
        super(message);
    }

    public InvalidAuthorizationTokenException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public InvalidAuthorizationTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthorizationTokenException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public InvalidAuthorizationTokenException(Throwable cause) {
        super(cause);
    }

    public InvalidAuthorizationTokenException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
