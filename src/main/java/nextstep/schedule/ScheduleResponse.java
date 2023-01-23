package nextstep.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ScheduleResponse {
    private final Long id;
    private final Theme theme;
    private final LocalDate date;
    private final LocalTime time;

    public static ScheduleResponse fromEntity(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .theme(schedule.getTheme())
                .date(schedule.getDate())
                .time(schedule.getTime())
                .build();
    }
}
