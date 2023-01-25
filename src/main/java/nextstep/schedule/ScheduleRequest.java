package nextstep.schedule;

import nextstep.support.InvalidInputException;
import nextstep.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ScheduleRequest {
    private Long themeId;
    private String date;
    private String time;

    public ScheduleRequest() {
    }

    public ScheduleRequest(Long themeId, String date, String time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Long getThemeId() {
        return themeId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Schedule toEntity(Theme theme) {
        try {
            return new Schedule(
                    theme,
                    LocalDate.parse(this.date),
                    LocalTime.parse(this.time)
            );
        } catch (DateTimeParseException e) {
            throw new InvalidInputException();
        }
    }
}
