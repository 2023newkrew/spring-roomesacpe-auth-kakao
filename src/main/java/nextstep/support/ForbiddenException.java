package nextstep.support;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("해당 권한이 없습니다.");
    }

}
