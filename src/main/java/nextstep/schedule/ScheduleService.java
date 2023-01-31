package nextstep.schedule;

import nextstep.exception.NotExistEntityException;
import nextstep.theme.Theme;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleDao scheduleDao;

    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public Long create(Schedule schedule, Theme theme) {
        return scheduleDao.save(schedule);
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public Schedule findById(Long id) {
        return scheduleDao.findById(id)
                .orElseThrow(() -> new NotExistEntityException("해당 스케쥴이 존재하지 않습니다."));
    }

    public void deleteById(Long id) {
        scheduleDao.deleteById(id);
    }
}
