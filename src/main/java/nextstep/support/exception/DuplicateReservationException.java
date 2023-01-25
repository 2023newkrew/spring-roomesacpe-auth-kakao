package nextstep.support.exception;

public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException() {
        super();
    }

    public DuplicateReservationException(String message) {
        super(message);
    }
}
