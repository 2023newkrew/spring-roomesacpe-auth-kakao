package nextstep.reservation;

import com.fasterxml.jackson.annotation.JsonCreator;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleResponse {
    private Long id;
    private Theme theme;
    private LocalDate date;
    private LocalTime time;

    @JsonCreator
    public ScheduleResponse(Long id, Theme theme, LocalDate date, LocalTime time) {
        this.id = id;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public static ScheduleResponse of(Schedule schedule) {
        return new ScheduleResponse(schedule.getId(), schedule.getTheme(), schedule.getDate(), schedule.getTime());
    }

    public Long getId() {
        return id;
    }

    public Theme getTheme() {
        return theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
