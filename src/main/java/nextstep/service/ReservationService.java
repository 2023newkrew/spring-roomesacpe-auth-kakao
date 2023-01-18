package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Member;
import nextstep.dao.ReservationDao;
import nextstep.domain.Schedule;
import nextstep.dao.ScheduleDao;
import nextstep.support.exception.AuthorizationException;
import nextstep.support.exception.DuplicateEntityException;
import nextstep.domain.Theme;
import nextstep.dao.ThemeDao;
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

    public Long create(long scheduleId, long memberId) {
        Schedule schedule = scheduleDao.findById(scheduleId);
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                memberId
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(long reservationId, long memberId) {
        Reservation reservation = reservationDao.findById(reservationId);
        if (reservation == null) {
            throw new NullPointerException();
        }
        if (!reservation.getMemberId().equals(memberId)) {
            throw new AuthorizationException();
        }
        reservationDao.deleteById(reservationId);
    }
}
