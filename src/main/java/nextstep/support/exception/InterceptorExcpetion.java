package nextstep.support.exception;

import org.springframework.http.HttpStatus;

public class InterceptorExcpetion extends RuntimeException {
    private HttpStatus status;

    public InterceptorExcpetion(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
