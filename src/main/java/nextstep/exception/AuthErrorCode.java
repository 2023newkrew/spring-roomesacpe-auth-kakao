package nextstep.exception;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 혹은 비밀번호가 잘못됐습니다."),
    TOKEN_VALIDATION_FAIL(HttpStatus.UNAUTHORIZED, "토큰 검증에 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
