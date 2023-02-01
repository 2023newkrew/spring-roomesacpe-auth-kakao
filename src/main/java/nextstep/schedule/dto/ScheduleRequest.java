package nextstep.schedule.dto;

import nextstep.schedule.domain.Schedule;
import nextstep.theme.domain.Theme;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleRequest {
    private Long themeId;
    private String date;
    private String time;

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public ScheduleRequest() {
    }

    public ScheduleRequest(Long themeId, String date, String time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public Long getThemeId() {
        return themeId;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getDate() {
        return date;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getTime() {
        return time;
    }

    public Schedule toEntity(Theme theme) {
        return new Schedule(
                theme,
                LocalDate.parse(this.date),
                LocalTime.parse(this.time)
        );
    }

    public boolean isValid() {
        return themeId != null && themeId > 0 && isValidDate(date) && isValidTime(time);
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch(DateTimeException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch(DateTimeException e) {
            return false;
        }
    }
}
