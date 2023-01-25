package nextstep.reservation.service;

import nextstep.global.exception.DuplicateEntityException;
import nextstep.global.exception.NotExistEntityException;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.repository.ScheduleRepository;
import nextstep.theme.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ScheduleRepository scheduleRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
        this.themeRepository = themeRepository;
    }

    public Long create(Long scheduleId, Long memberId) {
        Schedule targetSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotExistEntityException::new);

        List<Reservation> reservations = reservationRepository.findByScheduleId(scheduleId);
        if (!reservations.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation nextReservation = Reservation.of(targetSchedule, memberId);

        return reservationRepository.save(nextReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        themeRepository.findById(themeId)
                .orElseThrow(NotExistEntityException::new);

        return reservationRepository.findAllByThemeIdAndDate(themeId, date);
    }

    public void delete(Long id) {
        if (reservationRepository.deleteById(id) == 0) {
            throw new NotExistEntityException();
        }
    }
}
