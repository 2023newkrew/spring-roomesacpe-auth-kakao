package nextstep.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import nextstep.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class Schedule {
    private final Long id;
    @NonNull
    private final Theme theme;
    @NonNull
    private final LocalDate date;
    @NonNull
    private final LocalTime time;
}
