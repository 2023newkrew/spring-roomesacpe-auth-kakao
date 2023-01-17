package nextstep.support;

public class DuplicateReservationException extends DuplicateEntityException {
    public DuplicateReservationException() {
        super();
    }

    public DuplicateReservationException(String message) {
        super(message);
    }


    public DuplicateReservationException(String message, Throwable cause) {
        super(message, cause);
    }


    public DuplicateReservationException(Throwable cause) {
        super(cause);
    }
}