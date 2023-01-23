package nextstep.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import nextstep.schedule.Schedule;

@Builder
@Getter
public class Reservation {
    private final Long id;
    @NonNull
    private final Schedule schedule;
    @NonNull
    private final String name;
    @NonNull
    private final Long memberId;
}
