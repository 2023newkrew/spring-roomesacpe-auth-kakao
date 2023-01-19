package nextstep.schedule;

import nextstep.exception.BusinessException;
import nextstep.exception.ErrorCode;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
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

    public long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.THEME_NOT_FOUND));
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void cancel(long id) {
        scheduleDao.deleteById(id);
    }
}
