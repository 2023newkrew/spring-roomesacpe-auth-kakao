package nextstep.schedule.domain;

import nextstep.schedule.dto.ScheduleRequest;
import nextstep.schedule.persistence.ScheduleDao;
import nextstep.support.NotExistEntityException;
import nextstep.theme.domain.Theme;
import nextstep.theme.persistence.ThemeDao;
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
