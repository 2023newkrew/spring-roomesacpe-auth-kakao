package nextstep.schedule;

import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ScheduleResponse> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date).stream().map(e->e.toResponse()).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
