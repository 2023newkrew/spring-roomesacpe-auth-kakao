package nextstep.exception.business;

import org.springframework.http.HttpStatus;

public enum BusinessErrorCode {
    DELETE_FAILED_WHEN_NOT_MY_RESERVATION("본인의 예약만 삭제할 수 있습니다.", HttpStatus.UNAUTHORIZED),
    RESERVATION_ALREADY_EXIST_AT_THAT_TIME("해당 시간에 이미 예약이 존재합니다.", HttpStatus.CONFLICT),
    TOKEN_NOT_EXIST("토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_AVAILABLE("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    LOGIN_FAILED_WRONG_USERNAME_PASSWORD("아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED),
    MEMBER_ALREADY_EXIST_BY_USERNAME("해당 username을 가진 유저가 이미 존재합니다..", HttpStatus.CONFLICT),
    ACCESS_DENIED("해당 URL에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ;

    private final String message;
    private final HttpStatus httpStatus;

    BusinessErrorCode(String message, HttpStatus httpStatus) {
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
