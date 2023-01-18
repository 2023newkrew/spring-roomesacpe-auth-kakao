package nextstep.support;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("유효하지 않는 토큰입니다.");
    }
}
