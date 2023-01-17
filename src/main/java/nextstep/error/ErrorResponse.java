package nextstep.error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private final HttpStatus status;

    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
