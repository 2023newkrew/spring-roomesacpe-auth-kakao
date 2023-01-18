package nextstep.support;

public class NotExistEntityException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "존재하지 않는 데이터입니다.";

    public NotExistEntityException() {
        super(EXCEPTION_MESSAGE);
    }
}
