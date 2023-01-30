package nextstep.exceptions.exception.auth;

import org.springframework.http.HttpStatus;

public class NotAdminException extends AuthorizationException {
    public NotAdminException() {
        super("관리자가 아닙니다.");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
