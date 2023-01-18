package nextstep.exceptions.exception;

import org.springframework.http.HttpStatus;

public class NotExistEntityException extends RestAPIException {

    public NotExistEntityException() {
        this("해당 항목을 찾을 수 없습니다.");
    }

    public NotExistEntityException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
