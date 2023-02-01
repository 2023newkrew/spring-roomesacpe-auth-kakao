package nextstep.exception;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {

    INVALID_PASSWORD("아이디 혹은 비밀번호가 잘못됐습니다."),
    TOKEN_VALIDATION_FAIL("토큰 검증에 실패했습니다.");

    private final String message;

    AuthErrorCode(String message) {
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
