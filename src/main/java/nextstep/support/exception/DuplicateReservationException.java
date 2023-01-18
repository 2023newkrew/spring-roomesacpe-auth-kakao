package nextstep.support.exception;

public class DuplicateReservationException extends RuntimeException {
    private static final String MSG = "예약이 이미 존재합니다.";

    public DuplicateReservationException() {
        super(MSG);
    }
}
