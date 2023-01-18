package nextstep.support.exception;

public class NotExistThemeException extends RuntimeException {
    private static final String MSG = "해당 테마가 존재하지 않습니다.";

    public NotExistThemeException() {
        super(MSG);
    }
}
