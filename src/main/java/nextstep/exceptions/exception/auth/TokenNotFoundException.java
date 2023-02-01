package nextstep.exceptions.exception.auth;

public class TokenNotFoundException extends AuthorizationException {

    public TokenNotFoundException() {
        super("토큰이 존재하지 않습니다");
    }
}
