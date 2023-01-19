package nextstep.support.excpetion;

public class NotExistReservationException extends NotExistEntityException {
    public NotExistReservationException() {
        super("존재하지 않는 예약입니다.");
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
