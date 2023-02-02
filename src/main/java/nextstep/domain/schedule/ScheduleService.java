package nextstep.domain.schedule;

import nextstep.dto.schedule.ScheduleRequest;
import nextstep.persistence.schedule.Schedule;
import nextstep.persistence.schedule.ScheduleDao;
import nextstep.persistence.theme.Theme;
import nextstep.persistence.theme.ThemeDao;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId()).orElseThrow(NotExistEntityException::new);
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public int deleteById(Long id) {
        return scheduleDao.deleteById(id);
    }
}
