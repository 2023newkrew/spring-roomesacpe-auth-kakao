package nextstep.support;

public class NotExistThemeException extends RuntimeException {
    public NotExistThemeException() {
        super("존재하지 않는 테마 입니다.");
    }
}
