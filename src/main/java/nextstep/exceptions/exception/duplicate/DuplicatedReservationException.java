package nextstep.exceptions.exception.duplicate;

public class DuplicatedReservationException extends DuplicatedException {

    public DuplicatedReservationException() {
        super("중복된 예약이 존재합니다.");
    }
}
