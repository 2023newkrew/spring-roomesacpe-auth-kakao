package nextstep.service;

import nextstep.dao.ScheduleDao;
import nextstep.dao.ThemeDao;
import nextstep.domain.Schedule;
import nextstep.domain.Theme;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public Long create(LocalDate date, LocalTime time, long themeId) {
        Theme theme = themeDao.findById(themeId);
        Schedule schedule = new Schedule(theme, date, time);
        return scheduleDao.save(schedule);
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
