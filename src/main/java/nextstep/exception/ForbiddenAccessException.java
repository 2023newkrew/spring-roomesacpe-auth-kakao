package nextstep.exception;

public class ForbiddenAccessException extends CustomException {

    public ForbiddenAccessException() {
        super(ErrorCode.FORBIDDEN_ACCESS);
    }
}
