package nextstep.reservation;

import lombok.Builder;
import lombok.Getter;
import nextstep.schedule.Schedule;

@Builder
@Getter
public class Reservation {
    private Long id;
    private Schedule schedule;
    private String name;
}
