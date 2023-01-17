package nextstep.exception;

public class NotExistEntityException extends RoomEscapeException {
    public NotExistEntityException() {
        super();
    }

    public NotExistEntityException(String message) {
        super(message);
    }


    public NotExistEntityException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotExistEntityException(Throwable cause) {
        super(cause);
    }
}
