package nextstep.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nextstep.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequest {
    private Long themeId;
    private String date;
    private String time;

    public Schedule toEntityWithTheme(Theme theme) {
        return new Schedule(
                theme,
                LocalDate.parse(this.date),
                LocalTime.parse(this.time)
        );
    }

    public ScheduleRequest(Schedule schedule) {
        this.themeId = schedule.getTheme().getId();
        this.date = schedule.getDate().toString();
        this.time = schedule.getTime().toString();
    }
}
