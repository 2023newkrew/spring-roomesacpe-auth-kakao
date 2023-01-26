package nextstep.schedule;

import static nextstep.common.exception.ExceptionMessage.INVALID_THEME_ID;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.common.exception.NotExistEntityException;
import nextstep.schedule.dto.ScheduleRequestDto;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public Long create(ScheduleRequestDto scheduleRequestDto) {
        Theme theme = themeDao.findById(scheduleRequestDto.getThemeId())
            .orElseThrow(() -> new NotExistEntityException(INVALID_THEME_ID.getMessage()));
        return scheduleDao.save(scheduleRequestDto.toEntity(theme));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
