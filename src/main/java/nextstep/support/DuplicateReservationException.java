package nextstep.support;

public class DuplicateReservationException extends IllegalArgumentException {

    public DuplicateReservationException() {
    }

    @Override
    public String getMessage() {
        return "이미 예약된 시간입니다.";
    }

}
