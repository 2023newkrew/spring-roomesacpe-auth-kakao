package nextstep.schedule;

import nextstep.reservation.Reservation;
import nextstep.reservation.ReservationDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import javax.naming.ContextNotEmptyException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        if (themeList.get().isEmpty()) {
            throw new NullPointerException("Cannot Find Theme");
        }
        return scheduleDao.save(scheduleRequest.toEntity(themeList.get().get(0)));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) throws ContextNotEmptyException {
        List<Reservation> reservationList = reservationDao.findByScheduleId(id);
        if (Objects.requireNonNull(reservationList).size() != 0) {
            throw new ContextNotEmptyException("테마에 예약이 존재함");
        }
        scheduleDao.deleteById(id);
    }
}
