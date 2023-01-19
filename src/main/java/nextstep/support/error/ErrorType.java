package nextstep.support.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "접근 권한이 없습니다."),
    INVALID_MEMBER(HttpStatus.UNAUTHORIZED.value(), "아이디와 비밀번호를 다시 확인해주세요."),
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND.value(), "존재하지 않는 회원 입니다."),
    NOT_EXIST_RESERVATION(HttpStatus.NOT_FOUND.value(), "존재하지 않는 예약 입니다."),
    NOT_EXIST_SCHEDULE(HttpStatus.NOT_FOUND.value(), "존재하지 않는 스케줄 입니다."),
    NOT_EXIST_THEME(HttpStatus.NOT_FOUND.value(), "존재하지 않는 테마 입니다.");

    private final int statusCode;
    private final String message;

    ErrorType(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
