package nextstep.theme;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeDao themeDao;
    private final ScheduleDao scheduleDao;

    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) {
        List<Schedule> schedules = scheduleDao.findByThemeId(id);
        if (schedules.size() > 0) {
            throw new RoomReservationException(ErrorCode.THEME_CANT_BE_DELETED);
        }

        themeDao.delete(id);
    }
}
