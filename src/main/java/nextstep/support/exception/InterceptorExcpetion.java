package nextstep.support.exception;

import org.springframework.http.HttpStatus;

public class InterceptorExcpetion extends RuntimeException {
    private HttpStatus status;

    public InterceptorExcpetion(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
