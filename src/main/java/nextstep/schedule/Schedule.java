package nextstep.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import nextstep.theme.Theme;

@Builder
@Getter
public class Schedule {
    private Long id;
    private Theme theme;
    private LocalDate date;
    private LocalTime time;
}
