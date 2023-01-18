package nextstep.reservation;

public class ReservationResponse {
    private Long id;
    private Long scheduleId;
    private String name;

    public ReservationResponse(Long id, Long scheduleId, String name) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String getName() {
        return name;
    }
}
