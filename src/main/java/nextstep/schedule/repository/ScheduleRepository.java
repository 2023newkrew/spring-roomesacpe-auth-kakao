package nextstep.schedule.repository;

import nextstep.schedule.domain.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Long save(Schedule schedule);

    Optional<Schedule> findById(Long id);

    List<Schedule> findByThemeIdAndDate(Long themeId, String date);

    int deleteById(Long id);
}
