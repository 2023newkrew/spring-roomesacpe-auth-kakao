package nextstep.reservation;

import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.exception.api.DuplicateReservationException;
import nextstep.support.exception.api.NoSuchReservationException;
import nextstep.support.exception.api.NotReservationOwnerException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public Long create(ReservationRequest reservationRequest, Long memberId) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateReservationException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                reservationRequest.getName(),
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

    public void deleteById(Long id, Long memberId) {
        Reservation reservation = reservationDao.findById(id);

        if (reservation == null) {
            throw new NoSuchReservationException();
        }

        if(!Objects.equals(reservation.getMemberId(), memberId)) {
            throw new NotReservationOwnerException();
        }

        reservationDao.deleteById(id);
    }
}
