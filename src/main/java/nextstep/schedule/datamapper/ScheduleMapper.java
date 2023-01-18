package nextstep.schedule.datamapper;

import nextstep.schedule.dto.ScheduleResponse;
import nextstep.schedule.entity.ScheduleEntity;
import nextstep.theme.datamapper.ThemeMapper;
import nextstep.theme.entity.ThemeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    default ScheduleResponse entityToDtoResponse(ScheduleEntity scheduleEntity) {
        if (scheduleEntity == null) {
            return null;
        }

        Long id = scheduleEntity.getId();
        ThemeEntity themeEntity = scheduleEntity.getThemeEntity();
        LocalDate date = scheduleEntity.getDate();
        LocalTime time = scheduleEntity.getTime();

        return new ScheduleResponse(id, ThemeMapper.INSTANCE.entityToDto(themeEntity), date, time);
    }
}
