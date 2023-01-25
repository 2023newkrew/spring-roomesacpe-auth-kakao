package nextstep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotExistEntityException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "존재하지 않는 %s입니다.";

    public NotExistEntityException() {
    }

    public NotExistEntityException(String message) {
        super(String.format(MESSAGE_TEMPLATE, message));
    }
}

