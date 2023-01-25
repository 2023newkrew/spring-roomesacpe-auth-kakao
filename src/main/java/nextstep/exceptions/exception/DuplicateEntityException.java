package nextstep.exceptions.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends RestAPIException {

    public DuplicateEntityException() {
        this("이미 중복된 항목이 존재합니다.");
    }

    public DuplicateEntityException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
