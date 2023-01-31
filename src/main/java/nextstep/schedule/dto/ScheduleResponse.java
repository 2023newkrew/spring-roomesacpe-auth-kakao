package nextstep.schedule.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;
import nextstep.theme.dto.ThemeResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleResponse {
    private final Long id;
    private final ThemeResponse theme;
    private final LocalDate date;
    private final LocalTime time;

    private ScheduleResponse(Long id, Theme theme, LocalDate date, LocalTime time) {
        this.id = id;
        this.theme = ThemeResponse.of(theme);
        this.date = date;
        this.time = time;
    }

    public static ScheduleResponse of(Schedule schedule) {
        return new ScheduleResponse(schedule.getId(), schedule.getTheme(), schedule.getDate(), schedule.getTime());
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
