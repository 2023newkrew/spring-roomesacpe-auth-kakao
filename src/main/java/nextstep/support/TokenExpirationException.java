package nextstep.support;

public class TokenExpirationException extends IllegalArgumentException {
    public TokenExpirationException() {
    }

    @Override
    public String getMessage() {
        return "토큰이 만료되었습니다.";
    }
}
