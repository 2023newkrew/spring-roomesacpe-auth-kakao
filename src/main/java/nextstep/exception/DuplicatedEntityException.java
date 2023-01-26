package nextstep.exception;

public class DuplicatedEntityException extends CustomException {

    public DuplicatedEntityException() {
        super(ErrorCode.DUPLICATED_ENTITY);
    }
}
