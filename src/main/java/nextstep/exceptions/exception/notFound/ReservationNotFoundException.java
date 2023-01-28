package nextstep.exceptions.exception.notFound;

public class ReservationNotFoundException extends ObjectNotFoundException {
    public ReservationNotFoundException() {
        super("예약을 찾을 수 없습니다.");
    }
}
