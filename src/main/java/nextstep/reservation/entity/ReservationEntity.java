package nextstep.reservation.entity;

import lombok.Getter;
import nextstep.schedule.entity.ScheduleEntity;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Getter
public class ReservationEntity {

    private final Long id;
    private final AggregateReference<ScheduleEntity, Long> scheduleId;
    private final Long memberId;

    private ReservationEntity(Long id, AggregateReference<ScheduleEntity, Long> scheduleId, Long memberId) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.memberId = memberId;
    }

    public ReservationEntity(Long id, Long scheduleId, Long memberId) {
        this(id, AggregateReference.to(scheduleId), memberId);
    }

    public Long getScheduleId() {
        return scheduleId.getId();
    }
}
