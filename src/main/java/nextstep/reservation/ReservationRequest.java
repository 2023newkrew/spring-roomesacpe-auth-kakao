package nextstep.reservation;

public class ReservationRequest {
    private final Long scheduleId;
    private final String name;

    public ReservationRequest(Long scheduleId, String name) {
        this.scheduleId = scheduleId;
        this.name = name;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ReservationRequest{" +
                "scheduleId=" + scheduleId +
                ", name='" + name + '\'' +
                '}';
    }
}
