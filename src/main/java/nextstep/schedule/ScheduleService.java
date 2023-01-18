package nextstep.schedule;

import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ThemeDao themeDao;

    public ScheduleService(ScheduleDao scheduleRepository, ThemeDao themeDao) {
        this.scheduleRepository = scheduleRepository;
        this.themeDao = themeDao;
    }

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        return scheduleRepository.save(scheduleRequest.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleRepository.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }
}
