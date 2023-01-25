package nextstep.theme.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.dto.request.ThemeRequest;
import nextstep.theme.repository.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeAdminService {

    private final ThemeDao themeDao;

    private final ScheduleDao scheduleDao;

    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    public void delete(Long id) {
        List<Schedule> schedules = scheduleDao.findByThemeId(id);
        if (schedules.size() > 0) {
            throw new RoomReservationException(ErrorCode.THEME_CANT_BE_DELETED);
        }
        themeDao.delete(id);
    }
}
