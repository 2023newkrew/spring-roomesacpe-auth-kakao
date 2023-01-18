package nextstep.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.entity.Schedule;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    private Long id;
    private Schedule schedule;
    private Long memberId;
}
