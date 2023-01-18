package nextstep.exception;

import nextstep.error.ErrorCode;

public class NotExistEntityException extends RoomEscapeException {

    private ErrorCode errorCode;

    public NotExistEntityException() {
        super();
    }

    public NotExistEntityException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public NotExistEntityException(String message) {
        super(message);
    }
    public NotExistEntityException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotExistEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistEntityException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NotExistEntityException(Throwable cause) {
        super(cause);
    }

    public NotExistEntityException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
