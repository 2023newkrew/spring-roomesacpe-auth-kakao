package nextstep.schedule;

import nextstep.reservation.Reservation;
import nextstep.reservation.ReservationDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static nextstep.support.Messages.*;

@Service
public class ScheduleService {
    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;
    private final ReservationDao reservationDao;

    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao, ReservationDao reservationDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
        this.reservationDao = reservationDao;
    }

    public Long create(ScheduleRequest scheduleRequest) {
        Optional<List<Theme>> themeList = themeDao.findById(scheduleRequest.getThemeId());
        if (themeList.isEmpty() || themeList.get().isEmpty()) {
            throw new NullPointerException(THEME_NOT_FOUND.getMessage());
        }
        if (scheduleDao.isExistsByTimeAndDate(scheduleRequest.getTime(), scheduleRequest.getDate())) {
            throw new DuplicateKeyException(ALREADY_REGISTERED_SCHEDULE.getMessage());
        }
        return scheduleDao.save(scheduleRequest.toEntity(themeList.get().get(0)));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        List<Reservation> reservationList = reservationDao.findByScheduleId(id);
        if (scheduleDao.findById(id).isEmpty()){
            throw new NullPointerException(SCHEDULE_NOT_FOUND.getMessage());
        }
        if (Objects.requireNonNull(reservationList).size() != 0) {
            throw new DuplicateKeyException(ALREADY_REGISTERED_RESERVATION.getMessage());
        }
        scheduleDao.deleteById(id);
    }
}
