package nextstep.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다."),
    NOT_EXIST_THEME(HttpStatus.BAD_REQUEST, "없는 테마입니다."),
    NOT_EXIST_RESERVATION(HttpStatus.BAD_REQUEST, "없는 예약입니다."),
    DUPLICATE_ENTITY(HttpStatus.BAD_REQUEST, "이미 예약된 시간입니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
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
