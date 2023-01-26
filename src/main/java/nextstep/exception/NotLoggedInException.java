package nextstep.exception;

public class NotLoggedInException extends CustomException {

    public NotLoggedInException() {
        super(ErrorCode.NOT_LOGGED_IN);
    }
}
