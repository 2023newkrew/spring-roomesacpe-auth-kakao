package nextstep.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 공통 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다. 관리자에게 문의해 주세요."),
    API_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 API 요청입니다."),
    INVALID_BODY_FIELD(HttpStatus.BAD_REQUEST, "바디의 필드가 잘못된 형식이거나 누락되었습니다."),
    INVALID_PATH_VAR_OR_QUERY_PARAMETER(HttpStatus.BAD_REQUEST, "경로 변수나 쿼리 파라미터가 잘못된 형식이거나 누락되었습니다."),

    // 예약 에러
    DUPLICATE_RESERVATION(HttpStatus.BAD_REQUEST, "해당 시간에 예약이 존재합니다."),
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 ID의 예약이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
