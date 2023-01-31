package nextstep.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.exception.DuplicateEntityException;
import nextstep.exception.NotExistEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private ScheduleDao scheduleDao;
    private ThemeDao themeDao;

    public ScheduleService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public Long create(ScheduleRequest request) {
        Theme theme = themeDao.findById(request.getThemeId());

        if (theme == null) {
            throw new NotExistEntityException();
        }

        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime());

        if (scheduleDao.existsByThemeIdAndDateAndTime(theme.getId(), date, time)) {
            throw new DuplicateEntityException();
        }

        return scheduleDao.save(request.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
