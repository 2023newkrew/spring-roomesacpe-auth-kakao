package nextstep.reservation.service;

import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.entity.Reservation;
import nextstep.schedule.dao.ScheduleDao;
import nextstep.schedule.entity.Schedule;
import nextstep.support.DuplicateEntityException;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.Theme;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(Long scheduleId, Long memberId) {
        Schedule schedule = scheduleDao.findById(scheduleId);
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(scheduleId);
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                null,
                schedule,
                memberId
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void delete(Long id, Long memberId) {
        if (!reservationDao.existsByIdAndMemberId(id, memberId)) {
            throw new RuntimeException();
        }

        reservationDao.deleteById(id);
    }
}