package nextstep.reservation;

public class ReservationRequest {

    private long scheduleId;

    private ReservationRequest() {
    }

    public ReservationRequest(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getScheduleId() {
        return scheduleId;
    }

}
