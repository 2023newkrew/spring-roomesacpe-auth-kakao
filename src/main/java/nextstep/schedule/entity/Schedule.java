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
public class Schedule {

    private Long id;
    private Theme theme;
    private LocalDate date;
    private LocalTime time;
}
