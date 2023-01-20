package nextstep.schedule;

import nextstep.reservation.ReservationDao;
import nextstep.support.NotExistEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        if (theme == null) {
            throw new NullPointerException("테마를 찾을 수 없음");
        }
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        if (reservationDao.findByScheduleId(id) != null) {
            throw new NotExistEntityException("예약이 존재하여 삭제할 수 없음");
        }
        scheduleDao.deleteById(id);
    }
}
