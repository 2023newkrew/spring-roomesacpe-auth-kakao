package nextstep.exception;

public class NotExistReservationException extends NotExistEntityException {
    public NotExistReservationException() {
        super();
    }

    public NotExistReservationException(String message) {
        super(message);
    }


    public NotExistReservationException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotExistReservationException(Throwable cause) {
        super(cause);
    }
}
