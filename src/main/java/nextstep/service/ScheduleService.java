package nextstep.service;

import java.util.List;
import nextstep.entity.Schedule;
import nextstep.dto.schedule.ScheduleRequest;
import nextstep.repository.ScheduleDao;
import nextstep.entity.Theme;
import nextstep.repository.ThemeDao;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
