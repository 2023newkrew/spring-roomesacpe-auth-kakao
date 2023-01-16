package nextstep.reservation;

public class ReservationRequest {
    private final Long scheduleId;
    private final Long memberId;

    public ReservationRequest(Long scheduleId, Long memberId) {
        this.scheduleId = scheduleId;
        this.memberId = memberId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
