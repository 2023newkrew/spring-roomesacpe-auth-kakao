package nextstep.error;

public enum ErrorCode {
    THEME_NOT_FOUND(400, "해당 테마를 찾을 수 없습니다"),
    RESERVATION_NOT_FOUND(400, "해당 예약을 찾을 수 없습니다"),
    INVALID_USERNAME_PASSWORD(401, "잘못된 username 혹은 password 입니다"),
    NO_TOKEN(401, "로그인이 필요합니다"),
    INVALID_TOKEN(403, "권한이 없습니다"),
    DATABASE_SAVE_ERROR(500, "데이터베이스에 저장되지 않았습니다");
    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
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
