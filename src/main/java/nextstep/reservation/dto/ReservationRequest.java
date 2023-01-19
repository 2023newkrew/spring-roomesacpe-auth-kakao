package nextstep.reservation.dto;

import java.util.Objects;

public class ReservationRequest {
    private final Long scheduleId;
    private final Long memberId;

    public ReservationRequest(Long scheduleId, Long memberId) {
        validate();
        this.scheduleId = scheduleId;
        this.memberId = memberId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getMemberId() {
        return memberId;
    }

    private void validate() {
        validateScheduleId();
        validateMemberId();
    }

    private void validateScheduleId() {
        if (Objects.isNull(this.scheduleId)) {
            throw new IllegalArgumentException("scheduleId는 null 일수 없습니다.");
        }
    }

    private void validateMemberId() {
        if (Objects.isNull(this.memberId)) {
            throw new IllegalArgumentException("memberId는 null 일수 없습니다.");
        }

    }

}
