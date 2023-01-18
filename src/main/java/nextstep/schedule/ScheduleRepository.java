package nextstep.schedule;

import java.util.List;

public interface ScheduleRepository {
    Long save(Schedule schedule);

    Schedule findById(Long id);

    List<Schedule> findByThemeIdAndDate(Long themeId, String date);

    void deleteById(Long id);
}
