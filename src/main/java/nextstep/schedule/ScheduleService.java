package nextstep.schedule;

import lombok.RequiredArgsConstructor;
import nextstep.support.exception.RoomEscapeExceptionCode;
import nextstep.support.exception.ThemeException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId()).orElseThrow(
                () -> new ThemeException(RoomEscapeExceptionCode.NOT_FOUND_THEME)
        );
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    @Transactional(readOnly = true)
    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
