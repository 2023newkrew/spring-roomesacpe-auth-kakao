package nextstep.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.schedule.entity.ScheduleEntity;

@Getter
@AllArgsConstructor
public class ReservationEntity {

    private Long id;
    private ScheduleEntity schedule;
    private Long memberId;
}
