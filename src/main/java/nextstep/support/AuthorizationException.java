package nextstep.support;

public class AuthorizationException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "권한이 필요합니다.";

    public AuthorizationException() {
        super(EXCEPTION_MESSAGE);
    }
}
