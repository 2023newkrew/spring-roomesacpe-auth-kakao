package nextstep.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.theme.entity.ThemeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    private Long id;
    private ThemeEntity theme;
    private LocalDate date;
    private LocalTime time;

    public static ScheduleEntity of(ThemeEntity theme, LocalDate date, LocalTime time) {
        return new ScheduleEntity(null, theme, date, time);
    }
}
