package nextstep.schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.theme.ThemeResponse;

public class ScheduleResponse {

    private final Long id;
    private final ThemeResponse theme;
    private final LocalDate date;
    private final LocalTime time;

    @JsonCreator
    public ScheduleResponse(Long id, ThemeResponse theme, LocalDate date, LocalTime time) {
        this.id = id;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.theme = new ThemeResponse(schedule.getTheme());
        this.date = schedule.getDate();
        this.time = schedule.getTime();
    }

    public Long getId() {
        return id;
    }

    public ThemeResponse getTheme() {
        return theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
