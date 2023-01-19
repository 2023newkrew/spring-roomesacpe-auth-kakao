package nextstep.schedule;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleDao scheduleDao;

    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public List<Schedule> findByThemeIdAndDate(long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }
}
