package nextstep.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.theme.dto.ThemeResponse;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private Long id;
    private ThemeResponse theme;
    private LocalDate date;
    private LocalTime time;
}
