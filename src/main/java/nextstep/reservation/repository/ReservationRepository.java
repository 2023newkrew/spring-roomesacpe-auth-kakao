package nextstep.reservation.repository;

import nextstep.reservation.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Long save(Reservation reservation);

    List<Reservation> findAllByThemeIdAndDate(Long themeId, String date);

    Optional<Reservation> findById(Long id);

    List<Reservation> findByScheduleId(Long scheduleId);

    boolean existsByIdAndMemberId(Long id, Long memberId);

    int deleteById(Long id);
}