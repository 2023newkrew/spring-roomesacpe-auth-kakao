package nextstep.schedule;

import nextstep.error.ApplicationException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.error.ErrorType.SCHEDULE_NOT_FOUND;

@Service
public class ScheduleService {
    private ScheduleDao scheduleDao;
    private ThemeDao themeDao;

    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public Schedule findById(Long scheduleId) {
        Schedule schedule = scheduleDao.findById(scheduleId);
        if (schedule == null) {
            throw new ApplicationException(SCHEDULE_NOT_FOUND);
        }
        return schedule;
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
