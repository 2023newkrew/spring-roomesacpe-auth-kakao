package nextstep.support;

public class NotExistEntityException extends RuntimeException {

    public NotExistEntityException() {
        super("엔티티가 존재하지 않습니다.");
    }
}
