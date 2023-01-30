package nextstep.schedule.mapper;

import nextstep.schedule.domain.Schedule;
import nextstep.schedule.dto.ScheduleResponse;
import nextstep.schedule.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    default ScheduleResponse domainToDtoResponse(Schedule schedule) {
        if (schedule == null) {
            return null;
        }

        Long id = schedule.getId();
        Long themeId = schedule.getThemeId();
        LocalDate date = schedule.getDate();
        LocalTime time = schedule.getTime();

        return new ScheduleResponse(id, themeId, date, time);
    }

    List<ScheduleResponse> domainListToDtoResponseList(List<Schedule> schedules);

    Schedule entityToDomain(ScheduleEntity scheduleEntity);

    List<Schedule> entityListToDomainList(List<ScheduleEntity> scheduleEntities);

    ScheduleEntity domainToEntity(Schedule schedule);
}
