package nextstep.reservation;

import javax.validation.constraints.NotNull;

public class ReservationRequest {
    @NotNull
    private Long scheduleId;
    @NotNull
    private String name;

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
