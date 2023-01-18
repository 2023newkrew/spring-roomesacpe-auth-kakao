package nextstep.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.schedule.entity.Schedule;

@Getter
@AllArgsConstructor
public class Reservation {

    private Schedule schedule;
    private Long memberId;
}
