package nextstep.exception;

public enum ErrorCode {
    NOT_AUTHENTICATED(401, "인증 정보가 없습니다."),
    NOT_EXIST_THEME(400, "없는 테마입니다."),
    NOT_EXIST_RESERVATION(400, "없는 예약입니다."),
    DUPLICATE_ENTITY(400, "이미 예약된 시간입니다.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
