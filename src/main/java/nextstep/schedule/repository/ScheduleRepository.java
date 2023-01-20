package nextstep.schedule.repository;

import nextstep.schedule.entity.ScheduleEntity;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Long save(ScheduleEntity scheduleEntity);

    Optional<ScheduleEntity> findById(Long id);

    List<ScheduleEntity> findByThemeIdAndDate(Long themeId, String date);

    int deleteById(Long id);
}
