package nextstep.exception;

public class LoginFailException extends CustomException {

    public LoginFailException() {
        super(ErrorCode.LOGIN_FAIL);
    }
}
