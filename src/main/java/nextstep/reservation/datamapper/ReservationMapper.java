package nextstep.reservation.datamapper;

import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationResponse entityToDto(ReservationEntity reservationEntity);
}
