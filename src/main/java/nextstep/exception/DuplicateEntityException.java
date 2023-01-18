package nextstep.exception;

import nextstep.error.ErrorCode;

public class DuplicateEntityException extends RoomEscapeException {

    private ErrorCode errorCode;

    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntityException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }

    public DuplicateEntityException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
