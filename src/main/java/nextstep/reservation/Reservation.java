package nextstep.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.Schedule;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long id;
    private Schedule schedule;
    private String name;

    private Long memberId;

    public Reservation(Schedule schedule, String name, Long memberId) {
        this(null, schedule, name, memberId);
    }
}
