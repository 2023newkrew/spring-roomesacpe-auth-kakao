package nextstep.reservation;

import java.beans.ConstructorProperties;

public class ReservationRequest {

    private final Long scheduleId;

    @ConstructorProperties({"scheduleId"})
    public ReservationRequest(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

}
