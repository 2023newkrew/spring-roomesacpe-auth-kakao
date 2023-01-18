package nextstep.support;

public class DuplicateEntityException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "중복된 요청입니다.";

    public DuplicateEntityException() {
        super(EXCEPTION_MESSAGE);
    }
}
