package nextstep.error;

public enum ErrorCode {
    DUPLICATE_RESERVATION(400, "RESERVATION-001", "중복된 예약"),

    INVALID_TOKEN(401, "AUTH-001", "유효하지 않은 토큰"),
    TOKEN_EXPIRED(401, "AUTH-002", "만료된 토큰"),
    UNAUTHORIZED(401, "AUTH-003", "인증 실패"),
    MEMBER_NOT_FOUND(404, "AUTH-004", "존재하지 않는 사용자"),
    FORBIDDEN(403, "AUTH-005", "권한 부족");

    private final int status;
    private final String code;
    private final String description;

    ErrorCode(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
