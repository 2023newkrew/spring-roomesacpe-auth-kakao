package nextstep.exceptions.exception.auth;

import nextstep.exceptions.exception.RestAPIException;
import org.springframework.http.HttpStatus;

public class AuthorizationException extends RestAPIException {

    public AuthorizationException() {
        this("유효하지 않은 토큰입니다.");
    }

    public AuthorizationException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
