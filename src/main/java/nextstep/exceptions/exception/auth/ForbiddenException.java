package nextstep.exceptions.exception.auth;

import nextstep.exceptions.exception.RestAPIException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends RestAPIException {

    public ForbiddenException() {
        this("허가되지 않은 멤버입니다.");
    }

    public ForbiddenException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
