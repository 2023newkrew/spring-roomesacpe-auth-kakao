package nextstep.dto.response;

public class ErrorResponse {

    private int httpStatus;
    private String message;

    public static ErrorResponse of(int httpStatus, String message) {
        return new ErrorResponse(httpStatus, message);
    }

    private ErrorResponse(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
