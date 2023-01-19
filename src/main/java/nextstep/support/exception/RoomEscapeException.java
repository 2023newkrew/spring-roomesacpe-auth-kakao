package nextstep.support.exception;

import org.springframework.http.HttpStatus;

public class RoomEscapeException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public RoomEscapeException(RoomEscapeExceptionCode roomEscapeExceptionCode) {
        super(roomEscapeExceptionCode.getErrorMessage());

        this.httpStatus = roomEscapeExceptionCode.getHttpStatus();
        this.errorMessage = roomEscapeExceptionCode.getErrorMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
