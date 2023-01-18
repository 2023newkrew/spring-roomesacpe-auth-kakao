package nextstep.support;

public class ReservationNotFoundException extends IllegalArgumentException {
    public ReservationNotFoundException() {
    }

    @Override
    public String getMessage() {
        return "조건에 맞는 예약을 찾을 수 없습니다.";
    }
}
