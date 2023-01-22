package nextstep.exceptions.exception.notFound;

import nextstep.exceptions.exception.RestAPIException;
import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends RestAPIException {

    public ObjectNotFoundException() {
        this("객체를 찾을 수 없습니다.");
    }

    public ObjectNotFoundException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
