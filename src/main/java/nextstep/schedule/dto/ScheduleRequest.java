package nextstep.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;

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
        return Schedule.builder()
                .theme(theme)
                .date(LocalDate.parse(this.date))
                .time(LocalTime.parse(this.time))
                .build();
    }
}
