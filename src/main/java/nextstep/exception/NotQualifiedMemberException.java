package nextstep.exception;

import nextstep.error.ErrorCode;

public class NotQualifiedMemberException extends RoomEscapeException {

    private ErrorCode errorCode;

    public NotQualifiedMemberException() {
        super();
    }

    public NotQualifiedMemberException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public NotQualifiedMemberException(String message) {
        super(message);
    }

    public NotQualifiedMemberException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotQualifiedMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotQualifiedMemberException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NotQualifiedMemberException(Throwable cause) {
        super(cause);
    }

    public NotQualifiedMemberException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
