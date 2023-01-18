package nextstep.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.theme.entity.ThemeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ScheduleEntity {

    private Long id;
    private ThemeEntity themeEntity;
    private LocalDate date;
    private LocalTime time;

    public static ScheduleEntity of(ThemeEntity themeEntity, LocalDate date, LocalTime time) {
        return new ScheduleEntity(null, themeEntity, date, time);
    }
}
