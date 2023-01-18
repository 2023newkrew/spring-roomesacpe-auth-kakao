package nextstep.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.theme.entity.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private Theme theme;
    private LocalDate date;
    private LocalTime time;
}
