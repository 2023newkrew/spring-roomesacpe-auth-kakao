package nextstep.schedule.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.schedule.repository.ScheduleDao;
import nextstep.schedule.domain.Schedule;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleDao scheduleDao;

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }
}
