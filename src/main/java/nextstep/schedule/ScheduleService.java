package nextstep.schedule;

import nextstep.exceptions.exception.duplicate.DuplicatedScheduleException;
import nextstep.exceptions.exception.notFound.ScheduleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleDao scheduleDao;

    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public Long create(Schedule schedule) {
        scheduleDao.findBySchedule(schedule).ifPresent(obj -> {
            throw new DuplicatedScheduleException();
        });
        return scheduleDao.save(schedule);
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }

    public Schedule findById(Long id) {
        return scheduleDao.findById(id).orElseThrow(ScheduleNotFoundException::new);
    }

    public void deleteById(Long id) {
        scheduleDao.findById(id).orElseThrow(ScheduleNotFoundException::new);
        scheduleDao.deleteById(id);
    }
}
