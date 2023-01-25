package nextstep.reservation;

import java.util.List;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

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

    public Long create(ReservationRequest reservationRequest, String username) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new CustomException(ErrorCode.INVALID_SCHEDULE_ID);
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATED_ENTITY);
        }

        Reservation newReservation = new Reservation(
                schedule,
                username
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new CustomException(ErrorCode.INVALID_THEME_ID);
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, String username) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new CustomException(ErrorCode.NO_SUCH_ENTITY);
        }

        if (!reservation.isOwner(username)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        reservationDao.deleteById(id);
    }
}
