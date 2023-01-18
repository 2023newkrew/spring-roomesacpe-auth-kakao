package nextstep.reservation.service;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.schedule.dao.ScheduleDao;
import nextstep.schedule.entity.Schedule;
import nextstep.support.DuplicateEntityException;
import nextstep.support.NotExistEntityException;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeDao themeDao;
    private final ScheduleDao scheduleDao;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.reservationRepository = reservationRepository;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(Long scheduleId, Long memberId) {
        Schedule schedule = scheduleDao.findById(scheduleId);
        if (schedule == null) {
            throw new NotExistEntityException();
        }

        List<Reservation> reservation = reservationRepository.findByScheduleId(scheduleId);
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                memberId
        );

        return reservationRepository.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationRepository.findAllByThemeIdAndDate(themeId, date);
    }

    public void delete(Long id, Long memberId) {
        if (!reservationRepository.existsByIdAndMemberId(id, memberId)) {
            throw new RuntimeException();
        }

        reservationRepository.deleteById(id);
    }
}
