package nextstep.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.theme.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Theme theme;
    private LocalDate date;
    private LocalTime time;

    public static Schedule of(Theme theme, LocalDate date, LocalTime time) {

        return new Schedule(null, theme, date, time);
    }
}
