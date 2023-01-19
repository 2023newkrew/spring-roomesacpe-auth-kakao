package nextstep.support;

public class NotExistReservationException extends RuntimeException{
    public NotExistReservationException() {
        super("존재하지 않는 예약 입니다.");
    }
}
