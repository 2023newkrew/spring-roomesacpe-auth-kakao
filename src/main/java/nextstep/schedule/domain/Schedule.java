package nextstep.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Long themeId;
    private LocalDate date;
    private LocalTime time;

    public static Schedule of(Long themeId, LocalDate date, LocalTime time) {

        return new Schedule(null, themeId, date, time);
    }
}
