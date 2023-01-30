package nextstep.support.exception;

import org.springframework.http.HttpStatus;

public class ResolverException extends RuntimeException {
    private HttpStatus status;

    public ResolverException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
