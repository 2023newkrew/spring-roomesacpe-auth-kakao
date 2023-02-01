package nextstep.reservation.dto;

import java.util.Arrays;

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

    public boolean isValid() {
        return scheduleId != null && scheduleId > 0 && !isNullOrEmptyOrBlank(name);
    }

    private boolean isNullOrEmptyOrBlank(String... values) {
        return Arrays.stream(values)
                .anyMatch(value -> value == null || value.isEmpty() || value.isBlank());
    }
}
