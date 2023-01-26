package nextstep.exception;

public class NotExistEntityException extends CustomException {

    public NotExistEntityException() {
        super(ErrorCode.NOT_EXIST_ENTITY);
    }
}

