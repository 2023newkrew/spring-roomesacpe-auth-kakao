package nextstep.schedule;

import static nextstep.common.exception.ExceptionMessage.INVALID_THEME_ID;

import lombok.RequiredArgsConstructor;
import nextstep.common.exception.NotExistEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId())
            .orElseThrow(() -> new NotExistEntityException(INVALID_THEME_ID.getMessage()));
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
