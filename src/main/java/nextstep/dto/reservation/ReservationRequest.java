package nextstep.dto.reservation;

import java.util.Objects;

public class ReservationRequest {
    private final Long scheduleId;
    private final Long memberId;

    public static ReservationRequest createReservationRequest(Long scheduleId, Long memberId){
        validate(scheduleId, memberId);
        return new ReservationRequest(scheduleId, memberId);
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getMemberId() {
        return memberId;
    }


    private ReservationRequest(Long scheduleId, Long memberId) {
        this.scheduleId = scheduleId;
        this.memberId = memberId;
    }

    private static void validate(Long scheduleId, Long memberId) {
        validateScheduleId(scheduleId, "scheduleId는 null 일수 없습니다.");
        validateMemberId(memberId, "memberId는 null 일수 없습니다.");
    }

    private static void validateScheduleId(Long scheduleId, String s) {
        if (Objects.isNull(scheduleId)) {
            throw new IllegalArgumentException(s);
        }
    }

    private static void validateMemberId(Long scheduleId, String s) {
        if (Objects.isNull(scheduleId)) {
            throw new IllegalArgumentException(s);
        }
    }
}
