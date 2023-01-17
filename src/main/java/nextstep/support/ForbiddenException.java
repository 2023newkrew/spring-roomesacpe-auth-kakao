package nextstep.support;

public class ForbiddenException extends IllegalArgumentException {
    public ForbiddenException() {
    }

    @Override
    public String getMessage() {
        return "접근 권한이 없습니다.";
    }
}
