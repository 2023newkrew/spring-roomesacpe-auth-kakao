package nextstep.dto.request;

public class ReservationRequest {
    private long scheduleId;

    public ReservationRequest() {}

    public ReservationRequest(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getScheduleId() {
        return scheduleId;
    }
}