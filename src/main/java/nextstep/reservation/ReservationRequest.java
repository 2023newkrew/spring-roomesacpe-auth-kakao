package nextstep.reservation;

public class ReservationRequest {
    private Long scheduleId;
    private String name;

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public ReservationRequest() {
    }

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
}
