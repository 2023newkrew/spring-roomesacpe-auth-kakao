package nextstep.admin;

import nextstep.exception.DataAccessErrorCode;
import nextstep.exception.DataAccessException;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final ThemeDao themeDao;
    private final ScheduleDao scheduleDao;

    public AdminService(ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public long createTheme(AdminThemeRequest adminThemeRequest) {
        return themeDao.save(adminThemeRequest.toEntity());
    }

    public void deleteTheme(long id) {
        themeDao.findById(id)
                .orElseThrow(() -> new DataAccessException(DataAccessErrorCode.THEME_NOT_FOUND));

        themeDao.delete(id);
    }

    public long createSchedule(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId())
                .orElseThrow(() -> new DataAccessException(DataAccessErrorCode.THEME_NOT_FOUND));
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public void deleteSchedule(long id) {
        scheduleDao.deleteById(id);
    }
}
