package nextstep.support.exception;

public class NotUserOwnReservationException extends RuntimeException {
    public NotUserOwnReservationException() {
    }

    public NotUserOwnReservationException(String message) {
        super(message);
    }
}
