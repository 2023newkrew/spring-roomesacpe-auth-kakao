package nextstep.reservation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.schedule.Schedule;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ReservationResponse {
    private final Long id;
    private final Schedule schedule;
    private final String name;
    private final Long memberId;

    public static ReservationResponse fromEntity(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .schedule(reservation.getSchedule())
                .name(reservation.getName())
                .memberId(reservation.getMemberId())
                .build();
    }
}
