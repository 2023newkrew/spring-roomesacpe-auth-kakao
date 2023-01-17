package nextstep.support;

public class DuplicateNameException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "중복되는 이름입니다. 다시 요청해주세요.";

    public DuplicateNameException() {
        super(EXCEPTION_MESSAGE);
    }
}
