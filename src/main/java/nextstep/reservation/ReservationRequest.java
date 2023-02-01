package nextstep.reservation;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.PositiveOrZero;

public class ReservationRequest {
    @PositiveOrZero
    private final Long scheduleId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) // Single Argument Constructor 의 경우 잭슨이 모드를 DELEGATING 으로 착각함
    public ReservationRequest(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }
}
