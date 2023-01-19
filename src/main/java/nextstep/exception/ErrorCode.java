package nextstep.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    MEMBER_NOT_FOUND("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RESERVATION_NOT_FOUND("예약을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SCHEDULE_NOT_FOUND("스케줄을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    THEME_NOT_FOUND("테마를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DELETE_FAILED_WHEN_NOT_MY_RESERVATION("본인의 예약만 삭제할 수 있습니다.", HttpStatus.UNAUTHORIZED),
    RESERVATION_ALREADY_EXIST_AT_THAT_TIME("해당 시간에 이미 예약이 존재합니다.", HttpStatus.CONFLICT),
    TOKEN_NOT_EXIST("토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_AVAILABLE("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    LOGIN_FAILED_WRONG_USERNAME_PASSWORD("아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED),
    ;

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
