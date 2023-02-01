package nextstep.schedule.dto;

import nextstep.schedule.domain.Schedule;
import nextstep.theme.domain.Theme;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    public boolean isNotValid() {
        return Objects.isNull(themeId) || themeId <= 0 || isNotValidDate(date) || isNotValidTime(time);
    }

    private boolean isNotValidDate(String date) {
        try {
            LocalDate.parse(date);
            return false;
        } catch(DateTimeException e) {
            return true;
        }
    }

    private boolean isNotValidTime(String time) {
        try {
            LocalTime.parse(time);
            return false;
        } catch(DateTimeException e) {
            return true;
        }
    }
}
