package nextstep.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.theme.Theme;

public class Schedule {
    private final Long id;
    private final Theme theme;
    private final LocalDate date;
    private final LocalTime time;

    public Schedule(Theme theme, LocalDate date, LocalTime time) {
        this(null, theme, date, time);
    }

    public Schedule(Long id, Theme theme, LocalDate date, LocalTime time) {
        this.id = id;
        this.theme = theme;
        this.date = date;
        this.time = time;
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
