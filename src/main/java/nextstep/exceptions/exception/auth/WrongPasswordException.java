package nextstep.exceptions.exception.auth;

public class WrongPasswordException extends AuthorizationException {

    public WrongPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
