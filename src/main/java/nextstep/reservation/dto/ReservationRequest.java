package nextstep.reservation.dto;

import java.util.Arrays;
import java.util.Objects;

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

    public boolean isNotValid() {
        return Objects.isNull(scheduleId) || scheduleId <= 0 || isNullOrEmptyOrBlank(name);
    }

    private boolean isNullOrEmptyOrBlank(String... values) {
        return Arrays.stream(values)
                .anyMatch(value -> Objects.isNull(value) || value.isEmpty() || value.isBlank());
    }
}
