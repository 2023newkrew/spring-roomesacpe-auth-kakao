package nextstep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends IllegalArgumentException {
    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}

