package nextstep.exception;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException() {
        super("중복된 엔티티가 존재합니다.");
    }
}
