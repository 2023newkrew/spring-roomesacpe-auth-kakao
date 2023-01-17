package nextstep.schedule.service;

import nextstep.schedule.dao.ScheduleDao;
import nextstep.schedule.dto.ScheduleRequest;
import nextstep.schedule.entity.Schedule;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    @Autowired
    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        return scheduleDao.save(new Schedule(null, theme, LocalDate.parse(scheduleRequest.getDate()), LocalTime.parse(scheduleRequest.getTime())));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
