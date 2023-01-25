package nextstep.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.entity.Schedule;
import nextstep.entity.Theme;

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
