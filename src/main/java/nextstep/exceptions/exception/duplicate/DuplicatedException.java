package nextstep.exceptions.exception.duplicate;

import nextstep.exceptions.exception.RestAPIException;
import org.springframework.http.HttpStatus;

public class DuplicatedException extends RestAPIException {

    public DuplicatedException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
