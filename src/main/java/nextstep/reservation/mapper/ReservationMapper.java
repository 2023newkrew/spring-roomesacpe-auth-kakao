package nextstep.reservation.mapper;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.entity.ReservationEntity;
import nextstep.schedule.dto.ScheduleResponse;
import nextstep.schedule.mapper.ScheduleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    default ReservationResponse domainToDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        Long id = reservation.getId();
        ScheduleResponse scheduleResponse = ScheduleMapper.INSTANCE.domainToDtoResponse(reservation.getSchedule());
        Long memberId = reservation.getMemberId();

        return new ReservationResponse(id, scheduleResponse, memberId);
    }

    List<ReservationResponse> domainListToDtoList(List<Reservation> reservations);

    Reservation entityToDomain(ReservationEntity reservationEntity);

    List<Reservation> entityListToDomainList(List<ReservationEntity> reservationEntities);

    ReservationEntity domainToEntity(Reservation reservation);
}
