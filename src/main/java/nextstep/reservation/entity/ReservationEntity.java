package nextstep.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.entity.ScheduleEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    private Long id;
    private ScheduleEntity schedule;
    private Long memberId;

    public static ReservationEntity of(ScheduleEntity scheduleEntity, Long memberId) {
        return new ReservationEntity(null, scheduleEntity, memberId);
    }
}
