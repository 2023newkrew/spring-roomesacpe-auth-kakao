package nextstep.reservation;

import javax.validation.constraints.NotNull;

public class ReservationRequest {

    @NotNull
    private Long scheduleId;

    public ReservationRequest() {
    }

    public ReservationRequest(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }
}
