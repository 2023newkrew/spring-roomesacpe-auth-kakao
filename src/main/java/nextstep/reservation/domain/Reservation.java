package nextstep.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.schedule.domain.Schedule;

@Getter
@AllArgsConstructor
public class Reservation {

    private Long id;
    private Schedule schedule;
    private Long memberId;

    public static Reservation of(Schedule schedule, Long memberId) {

        return new Reservation(null, schedule, memberId);
    }
}
