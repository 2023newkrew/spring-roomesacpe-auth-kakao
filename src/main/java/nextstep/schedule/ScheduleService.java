package nextstep.schedule;

import nextstep.exception.NotExistEntityException;
import nextstep.schedule.dto.ScheduleRequest;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleDao scheduleDao;

    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public Long create(ScheduleRequest scheduleRequest, Theme theme) {
        return scheduleDao.save(scheduleRequest.toEntity(theme));
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
