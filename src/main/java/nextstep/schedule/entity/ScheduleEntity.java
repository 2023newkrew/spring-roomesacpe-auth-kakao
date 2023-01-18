package nextstep.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.theme.entity.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    private Long id;
    private Theme theme;
    private LocalDate date;
    private LocalTime time;

    public static ScheduleEntity of(Theme theme, LocalDate date, LocalTime time) {
        return new ScheduleEntity(null, theme, date, time);
    }
}
