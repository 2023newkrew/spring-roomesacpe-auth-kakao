package nextstep.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class ScheduleRequestDto {

    @NotNull(message = "테마아이디를 입력해주세요.")
    private Long themeId;

    @NotBlank(message = "생성할 스케줄 날짜를 입력해주세요.")
    private LocalDate date;

    @NotBlank(message = "생성할 스케줄 시간을 입력해주세요.")
    private LocalTime time;

    public Schedule toEntity(Theme theme) {
        return new Schedule(
            theme,
            date,
            time);
    }
}
