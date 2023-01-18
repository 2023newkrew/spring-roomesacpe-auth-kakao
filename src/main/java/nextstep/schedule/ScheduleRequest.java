package nextstep.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class ScheduleRequest {
    private Long themeId;
    private String date;
    private String time;

    public Schedule toEntity(Theme theme) {
        return Schedule.builder()
                .theme(theme)
                .date(LocalDate.parse(this.date))
                .time(LocalTime.parse(this.time))
                .build();
    }
}
