package nextstep.support;

public class UnauthorizedException extends IllegalArgumentException {
    public UnauthorizedException() {
    }

    @Override
    public String getMessage() {
        return "접근 권한이 없습니다.";
    }
}
