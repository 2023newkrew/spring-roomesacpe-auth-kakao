package nextstep.support.exception;

public class NotExistReservationException extends RuntimeException {
    public NotExistReservationException() {
    }

    public NotExistReservationException(String message) {
        super(message);
    }
}
