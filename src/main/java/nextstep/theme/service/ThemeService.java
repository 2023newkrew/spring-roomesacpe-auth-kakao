package nextstep.theme.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.repository.ScheduleDao;
import nextstep.theme.dto.request.ThemeRequest;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeDao themeDao;

    public List<Theme> findAll() {
        return themeDao.findAll();
    }
}
