package nextstep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginInformationException extends RuntimeException {
    public LoginInformationException() {
    }

    public LoginInformationException(String message) {
        super(message);
    }
}
