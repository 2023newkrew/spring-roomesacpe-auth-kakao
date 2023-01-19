package nextstep.service;

import nextstep.dao.ScheduleDao;
import nextstep.dao.ThemeDao;
import nextstep.domain.Schedule;
import nextstep.domain.Theme;
import nextstep.dto.request.ScheduleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private ScheduleDao scheduleDao;
    private ThemeDao themeDao;

    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(long id) {
        scheduleDao.deleteById(id);
    }
}
