package nextstep.schedule.datamapper;

import nextstep.schedule.dto.ScheduleResponse;
import nextstep.schedule.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    ScheduleResponse entityToDtoResponse(ScheduleEntity scheduleEntity);
}
