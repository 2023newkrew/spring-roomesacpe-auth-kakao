package nextstep.reservation.datamapper;

import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.entity.ReservationEntity;
import nextstep.schedule.datamapper.ScheduleMapper;
import nextstep.schedule.dto.ScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    default ReservationResponse entityToDto(ReservationEntity reservationEntity) {
        if (reservationEntity == null) {
            return null;
        }

        Long id = reservationEntity.getId();
        ScheduleResponse scheduleResponse = ScheduleMapper.INSTANCE.entityToDtoResponse(reservationEntity.getSchedule());
        Long memberId = reservationEntity.getMemberId();

        return new ReservationResponse(id, scheduleResponse, memberId);
    }
}
