package nextstep.reservation.datamapper;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    Reservation entityToDomain(ReservationEntity reservationEntity);

    default ReservationEntity domainToEntity(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return new ReservationEntity(
                null,
                reservation.getSchedule(),
                reservation.getMemberId()
        );
    }
}
