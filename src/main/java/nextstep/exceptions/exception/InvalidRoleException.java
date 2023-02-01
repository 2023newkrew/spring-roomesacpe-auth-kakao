package nextstep.exceptions.exception;

import org.springframework.http.HttpStatus;

public class InvalidRoleException extends RestAPIException {

    public InvalidRoleException() {
        this("유효하지 않은 Role 입니다.");
    }

    public InvalidRoleException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
