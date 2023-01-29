package nextstep.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorityException extends RuntimeException {
    public AuthorityException() {

    }

    public AuthorityException(String message) {
        super(message);
    }
}
