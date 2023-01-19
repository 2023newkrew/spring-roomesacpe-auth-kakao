package nextstep.support.excpetion;

public class DuplicateReservationException extends DuplicateEntityException {
    public DuplicateReservationException() {
        super("중복된 시간에 예약을 할 수 없습니다.");
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