package nextstep.service;

import nextstep.error.ApplicationException;
import nextstep.domain.schedule.Schedule;
import nextstep.domain.schedule.ScheduleDao;
import nextstep.dto.request.ScheduleRequest;
import nextstep.domain.theme.Theme;
import nextstep.domain.theme.ThemeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    @Transactional(readOnly = true)
    public Schedule findById(Long scheduleId) {
        Schedule schedule = scheduleDao.findById(scheduleId);
        if (schedule == null) {
            throw new ApplicationException(SCHEDULE_NOT_FOUND);
        }
        return schedule;
    }

    @Transactional(readOnly = true)
    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    @Transactional
    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
