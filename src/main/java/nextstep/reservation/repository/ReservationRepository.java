package nextstep.reservation.repository;

import nextstep.reservation.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Long save(ReservationEntity reservationEntity);

    List<ReservationEntity> findAllByThemeIdAndDate(Long themeId, String date);

    Optional<ReservationEntity> findById(Long id);

    List<ReservationEntity> findByScheduleId(Long scheduleId);

    int deleteById(Long id);
}